package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

/**
 * This class provides functionality to obfuscate HTTP requests and responses.
 *
 * <p>The values of the headers with keys defined in {@link StaticConfig#HEADERS_TO_OBFUSCATE} are
 * replaced by {@link StaticConfig#OBFUSCATION_VALUE}.
 *
 * <p>The values of the cookies with name defined in {@link StaticConfig#COOKIES_TO_OBFUSCATE} are
 * replaced by {@link StaticConfig#OBFUSCATION_VALUE}.
 *
 * <p>The attributes of the body with names defined in
 * {@link StaticConfig#BODY_ATTRIBUTES_TO_OBFUSCATE} are replaced by {@link
 * StaticConfig#OBFUSCATION_VALUE}.
 */
public class LogObfuscator {

  private final ObjectMapper mapper;
  private final Pattern objectAttributePattern;
  private final Pattern inlineAttributePattern;
  private final Pattern findNextParenthesesPattern;

  /**
   * Constructor.
   */
  LogObfuscator() {
    this(
        StaticConfig.BODY_ATTRIBUTES_TO_OBFUSCATE,
        new ObjectMapper()
            .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setSerializationInclusion(Include.NON_EMPTY));
  }

  LogObfuscator(final List<String> attributesToObfuscate, ObjectMapper mapper) {
    this.mapper = mapper;
    String attributeNameRegex = String.join("|", attributesToObfuscate);
    String attributeWithoutValueRegex = String.format("\"(:?%s)\"\\s*:\\s*", attributeNameRegex);
    objectAttributePattern =
        Pattern.compile(String.format("%s%s", attributeWithoutValueRegex, "(?![\\s\"<])"));
    String inlineAttributePatternRegex = String.format(
        "(?<notToReplace>%s)(?<toReplace>\".*?)\"",
        attributeWithoutValueRegex);
    inlineAttributePattern = Pattern.compile(inlineAttributePatternRegex);
    findNextParenthesesPattern = Pattern.compile("[()\\[\\]{}]");
  }

  String obfuscateHeaders(Map<String, List<String>> headers) throws JsonProcessingException {
    Map<String, List<String>> obfuscated = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
      String headerKey = entry.getKey();
      final List<String> headerValues = entry.getValue();
      if (StaticConfig.HEADERS_TO_OBFUSCATE.contains(headerKey)) {
        obfuscated.put(headerKey, List.of(StaticConfig.OBFUSCATION_VALUE));
      } else {
        obfuscated.put(headerKey, headerValues);
      }
    }
    return mapper.writeValueAsString(obfuscated);
  }

  String obfuscateCookies(Collection<Cookie> cookies) throws JsonProcessingException {
    List<Cookie> anonymize = new ArrayList<>();
    for (final Cookie cookie : cookies) {
      final String cookieName = cookie.getName();
      if (StaticConfig.COOKIES_TO_OBFUSCATE.contains(cookieName)) {
        anonymize.add(copyCookieWithObfuscatedValue(cookie));
      } else {
        anonymize.add(cookie);
      }
    }
    return mapper.writeValueAsString(anonymize);
  }

  private Cookie copyCookieWithObfuscatedValue(Cookie cookie) {
    return new Cookie(
        cookie.getName(),
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getVersion());
  }

  String obfuscateNewCookies(Collection<NewCookie> newCookies) throws JsonProcessingException {
    List<NewCookie> obfuscated = new ArrayList<>();
    for (final NewCookie cookie : newCookies) {
      final String cookieName = cookie.getName();
      if (StaticConfig.COOKIES_TO_OBFUSCATE.contains(cookieName)) {
        obfuscated.add(constructNewCookieWithObfuscatedValue(cookie));
      } else {
        obfuscated.add(cookie);
      }
    }
    return mapper.writeValueAsString(obfuscated);
  }

  private NewCookie constructNewCookieWithObfuscatedValue(NewCookie cookie) {
    return new NewCookie(
        cookie.getName(),
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getVersion(),
        StaticConfig.OBFUSCATION_VALUE,
        cookie.getMaxAge(),
        cookie.getExpiry(),
        cookie.isSecure(),
        cookie.isHttpOnly());
  }

  String obfuscateEntityString(Object entity) throws IOException {
    String entityString = inlineAttributePattern.matcher(normalizeInput(entity))
        .replaceAll(String.format("${notToReplace}%s", StaticConfig.OBFUSCATION_VALUE));
    Matcher matcher = objectAttributePattern.matcher(entityString);
    ArrayList<MatchInterval> intervals = findMatchIntervals(entityString, matcher);
    return replaceIntervalsWithObfuscation(entityString, intervals)
        .replaceAll("\n", "\\\\n")
        .replaceAll("\r", "\\\\r")
        .replaceAll("\t", "\\\\t");
  }

  private String normalizeInput(Object entity) throws JsonProcessingException {
    if (entity instanceof String) {
      return (String) entity;
    }
    ObjectWriter writer = mapper.writer();
    return writer.writeValueAsString(entity);
  }

  private ArrayList<MatchInterval> findMatchIntervals(
      String entityString,
      Matcher matcher) {
    ArrayList<MatchInterval> intervals = new ArrayList<>();
    int currentHighest = 0;
    while (matcher.find()) {
      int matchEndsAt = matcher.end();
      if (currentHighest > matchEndsAt) {
        continue;
      }
      String rest = entityString.substring(matchEndsAt);
      MatchInterval interval = findInterval(matchEndsAt, rest);
      intervals.add(interval);
    }
    intervals.sort(Comparator.comparingInt(MatchInterval::getStart).reversed());
    return intervals;
  }

  private MatchInterval findInterval(int indexOfFirstCharacter, String rest) {
    BalancedParenthesisParameters parameters = new BalancedParenthesisParameters();
    Matcher matcher = findNextParenthesesPattern.matcher(rest);
    while (matcher.find()) {
      analyzeCharacter(rest.charAt(matcher.start()), parameters);
      if (parameters.areAllClosed()) {
        int lengthOfMatch = matcher.end();
        return MatchInterval.builder()
            .start(indexOfFirstCharacter)
            .end(indexOfFirstCharacter + lengthOfMatch)
            .build();
      }
    }
    throw new IllegalStateException("the Json String is not properly formatted");
  }

  private void analyzeCharacter(char character, BalancedParenthesisParameters parameters) {
    switch (character) {
      case '(':
        parameters.incrementNumberOfUnclosedParenthesis();
        break;
      case ')':
        parameters.decrementNumberOfUnclosedParenthesis();
        break;
      case '[':
        parameters.incrementNumberOfUnclosedBrackets();
        break;
      case ']':
        parameters.decrementNumberOfUnclosedBrackets();
        break;
      case '{':
        parameters.incrementNumberOfUnclosedBraces();
        break;
      case '}':
        parameters.decrementNumberOfUnclosedBraces();
        break;
      default:
    }
  }

  private String replaceIntervalsWithObfuscation(
      String entityString,
      List<MatchInterval> intervals) {
    StringBuilder result = new StringBuilder(entityString);
    intervals.forEach(interval -> result.replace(
        interval.getStart(),
        interval.getEnd(),
        String.format("%s", StaticConfig.OBFUSCATION_VALUE)));
    return result.toString();
  }
}
