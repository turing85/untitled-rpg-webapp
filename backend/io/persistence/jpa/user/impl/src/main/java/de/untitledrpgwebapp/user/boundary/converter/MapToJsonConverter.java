package de.untitledrpgwebapp.user.boundary.converter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MapToJsonConverter implements AttributeConverter<Map<String, Object>, String> {

  public static final String SERIALIZATION_EXCEPTION_MESSAGE_FORMAT =
      "Something went horrible wrong. Somehow, the map did not serialize to a proper json "
          + "object: %s";
  public static final String DESERIALIZATION_EXCEPTION_MESSAGE_FORMAT =
      "Something went horrible wrong. Somehow, the database data is not a valid json "
          + "object: %s";
  private final ObjectMapper mapper;

  public MapToJsonConverter() {
    this(new ObjectMapper().setSerializationInclusion(Include.NON_EMPTY));
  }

  @Override
  public String convertToDatabaseColumn(Map<String, Object> map) {
    if (Objects.isNull(map) || map.isEmpty()) {
      return "";
    }
    try {
      return mapper.writeValueAsString(map);
    } catch (JsonProcessingException exception) {
      throw new IllegalStateException(
          String.format(
              SERIALIZATION_EXCEPTION_MESSAGE_FORMAT,
              map),
          exception);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, Object> convertToEntityAttribute(String dbData) {
    if (Objects.isNull(dbData) || dbData.isBlank()) {
      return new HashMap<>();
    }
    try {
      return mapper.readValue(dbData, Map.class);
    } catch (JsonProcessingException exception) {
      throw new IllegalStateException(
          String.format(
              DESERIALIZATION_EXCEPTION_MESSAGE_FORMAT,
              dbData),
          exception);
    }
  }
}
