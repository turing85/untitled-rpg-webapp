package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LogObfuscator unit.")
class LogObfuscatorTest {

  public static final String HEADER_ATTRIBUTE_TO_OBFUSCATE = "headerAttributeToObfuscate";
  public static final String COOKIE_ATTRIBUTE_TO_OBFUSCATE = "cookieAttributeToObfuscate";
  public static final String BODY_ATTRIBUTE_TO_OBFUSCATE_ONE = "bodyAttributeToObfuscateOne";
  public static final String BODY_ATTRIBUTE_TO_OBFUSCATE_TWO = "bodyAttributeToObfuscateTwo";
  public static final String OBFUSCATION_VALUE = "<obfuscationValue>";
  public static final String RESULT = "result";

  private final ObjectMapper mapper = mock(ObjectMapper.class);
  private final LogObfuscator uut = new LogObfuscator(
      List.of(HEADER_ATTRIBUTE_TO_OBFUSCATE),
      List.of(COOKIE_ATTRIBUTE_TO_OBFUSCATE),
      List.of(BODY_ATTRIBUTE_TO_OBFUSCATE_ONE, BODY_ATTRIBUTE_TO_OBFUSCATE_TWO),
      OBFUSCATION_VALUE,
      mapper);

  @BeforeEach
  void setup() throws JsonProcessingException {
    when(mapper.writeValueAsString(any())).thenReturn(RESULT);
  }

  @Test
  @DisplayName("Should return obfuscated headers when obfuscateHeaders is called.")
  void shouldReturnObfuscatedHeadersWhenObfuscateHeadersIsCalled() throws JsonProcessingException {
    // GIVEN
    String attributeNotToObfuscate = "attributeNotToObfuscate";
    List<String> valuesNotToObfuscate = List.of("values", "not", "to", "obfuscate");
    List<String> valuesToObfuscate = List.of("values", "to", "obfuscate");

    HashMap<String, List<String>> headers = new HashMap<>();
    headers.put(attributeNotToObfuscate, valuesNotToObfuscate);
    headers.put(HEADER_ATTRIBUTE_TO_OBFUSCATE, valuesToObfuscate);

    // WHEN
    String actual = uut.obfuscateHeaders(headers);

    // THEN
    assertThat(actual, is(RESULT));

    verify(mapper).writeValueAsString(argThat(object -> {
      assertThat(object, instanceOf(Map.class));
      @SuppressWarnings("unchecked")
      Map<String, List<String>> obfuscatedHeaders = (Map<String, List<String>>) object;
      Set<String> keySet = obfuscatedHeaders.keySet();
      assertThat(keySet, hasSize(headers.size()));
      assertThat(
          keySet,
          containsInAnyOrder(attributeNotToObfuscate, HEADER_ATTRIBUTE_TO_OBFUSCATE));
      assertThat(obfuscatedHeaders.get(attributeNotToObfuscate), is(valuesNotToObfuscate));
      List<String> obfuscated = obfuscatedHeaders.get(HEADER_ATTRIBUTE_TO_OBFUSCATE);
      assertThat(obfuscated, hasSize(1));
      assertThat(obfuscated.get(0), is(OBFUSCATION_VALUE));
      return true;
    }));
  }

  @Test
  @DisplayName("Should return obfuscated cookies when obfuscateCookies is called.")
  void shouldReturnObfuscatedCookiesWhenObfuscateCookiesIsCalled() throws JsonProcessingException {
    // GIVEN
    String cookieNameNotToObfuscate = "cookieNameNotToObfuscate";
    String cookieValueNotToObfuscate = "cookieValueNotToObfuscate";
    Cookie cookieNotToObfuscate =
        new Cookie(cookieNameNotToObfuscate, cookieValueNotToObfuscate);
    Cookie cookieToObfuscate = new Cookie(COOKIE_ATTRIBUTE_TO_OBFUSCATE, "doesNotMatter");

    Cookie obfuscatedCooke = new Cookie(COOKIE_ATTRIBUTE_TO_OBFUSCATE, OBFUSCATION_VALUE);
    // WHEN
    List<Cookie> cookies = List.of(cookieToObfuscate, cookieNotToObfuscate);
    String actual = uut.obfuscateCookies(cookies);

    // THEN
    assertThat(actual, is(RESULT));

    // THEN
    verify(mapper).writeValueAsString(argThat(object -> {
      assertThat(object, instanceOf(List.class));
      @SuppressWarnings("unchecked")
      List<Cookie> obfuscatedCookies = (List<Cookie>) object;
      assertThat(obfuscatedCookies, hasSize(cookies.size()));
      assertThat(
          obfuscatedCookies,
          containsInAnyOrder(cookieNotToObfuscate, obfuscatedCooke));
      return true;
    }));
  }

  @Test
  @DisplayName("Should return obfuscated new cookies when obfuscateNewCookies is called.")
  void shouldReturnObfuscatedNewCookiesWhenObfuscateNewCookiesIsCalled()
      throws JsonProcessingException {
    // GIVEN
    String cookieNameNotToObfuscate = "cookieNameNotToObfuscate";
    String cookieValueNotToObfuscate = "cookieValueNotToObfuscate";
    NewCookie newCookieNotToObfuscate =
        new NewCookie(cookieNameNotToObfuscate, cookieValueNotToObfuscate);
    NewCookie newCookieToObfuscate = new NewCookie(COOKIE_ATTRIBUTE_TO_OBFUSCATE, "doesNotMatter");

    NewCookie obfuscatedCooke = new NewCookie(COOKIE_ATTRIBUTE_TO_OBFUSCATE, OBFUSCATION_VALUE);

    // WHEN
    List<NewCookie> newCookies = List.of(newCookieToObfuscate, newCookieNotToObfuscate);
    String actual = uut.obfuscateNewCookies(newCookies);

    // THEN
    assertThat(actual, is(RESULT));

    verify(mapper).writeValueAsString(argThat(object -> {
      assertThat(object, instanceOf(List.class));
      @SuppressWarnings("unchecked")
      List<NewCookie> obfuscatedCookies = (List<NewCookie>) object;
      assertThat(obfuscatedCookies, hasSize(newCookies.size()));
      assertThat(
          obfuscatedCookies,
          containsInAnyOrder(newCookieNotToObfuscate, obfuscatedCooke));
      return true;
    }));
  }

  @Test
  @DisplayName("Should return obfuscated entity string when obfuscateEntityString is called.")
  void shouldReturnObfuscatedEntityStringWhenObfuscateEntityStringIsCalled() throws IOException {
    // GIVEN
    String input = "{"
            + "\"attributeNotToObfuscate\":\"notObfuscated\","
            + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_ONE + "\":\"inline\","
            + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_TWO + "\":{"
                + "\"someInnerAttribute\" : [\"array\", \"values\"],"
                + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_TWO + "\":{"
                    + "\"someNested\":\"attributes\""
                + "}"
            + "}"
        + "}";
    String expected = "{"
        + "\"attributeNotToObfuscate\":\"notObfuscated\","
        + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_ONE + "\":" + OBFUSCATION_VALUE + ","
        + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_TWO + "\":" + OBFUSCATION_VALUE +""
        + "}";

    // WHEN
    String actual = uut.obfuscateEntityString(input);

    // THEN
    assertThat(actual, is(expected));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when JSON is malformed.")
  void shouldThrowIllegalArgumentExceptionWhenJsonIsMalformed() {
    // GIVEN
    String input = "{"
            + "\"attributeNotToObfuscate\":\"notObfuscated\","
            + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_ONE + "\":\"inline\","
            + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_TWO + "\":{"
                + "\"someInnerAttribute\" : [\"array\", \"values\"],"
                + "\"" + BODY_ATTRIBUTE_TO_OBFUSCATE_TWO + "\":{"
                    + "\"someNested\":\"attributes\""
                + "}";

    // WHEN
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> uut.obfuscateEntityString(input));

    // THEN
    assertThat(exception.getMessage(), is(LogObfuscator.JSON_MALFORMED_MESSAGE));
  }
}