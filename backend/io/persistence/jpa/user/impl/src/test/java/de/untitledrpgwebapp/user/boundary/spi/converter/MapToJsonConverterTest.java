package de.untitledrpgwebapp.user.boundary.spi.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for MapToJsonConverter unit.")
class MapToJsonConverterTest {

  private final ObjectMapper mapper = mock(ObjectMapper.class);
  private final MapToJsonConverter uut = new MapToJsonConverter(mapper);
  private final String dbData = "dbData";
  private final HashMap<String, Object> map = new HashMap<>();
  private final JsonProcessingException toBeThrown = mock(JsonProcessingException.class);

  @BeforeEach
  void setup() {
    map.put("some", "data");
  }

  @Test
  @DisplayName("Should construct Object with an mapper when default constructor is called")
  void shouldConstructObjectWithAndMapperWhenDefaultConstructorIsCalled() {
    // GIVEN: nothing

    // WHEN
    MapToJsonConverter actual = new MapToJsonConverter();

    // THEN
    assertThat(actual.getMapper(), is(notNullValue()));
  }

  @Test
  @DisplayName("Should call mapper with expected Argument when convertToDatabaseColumn is called.")
  void shouldCallMapperWithExpectedArgumentWhenConvertToDatabaseColumnIsCalled()
      throws JsonProcessingException {
    // GIVEN
    when(mapper.writeValueAsString(any(Map.class))).thenReturn(dbData);

    // WHEN
    String actual = uut.convertToDatabaseColumn(map);

    // THEN
    assertThat(actual, is(dbData));

    verify(mapper).writeValueAsString(map);
  }

  @Test
  @DisplayName("Should return an empty string when convertToDatabaseColumn is called with an empty "
      + "map.")
  void shouldMapToEmptyStringWhenConvertToDatabaseColumnIsCalledWithEmptyMap() {
    // GIVEN: nothing

    // WHEN
    String actual = uut.convertToDatabaseColumn(new HashMap<>());

    // THEN
    assertTrue(actual.isBlank());

    verifyNoInteractions(mapper);
  }

  @Test
  @DisplayName("Should return an empty string when convertToDatabaseColumn is called with null.")
  void shouldMapToEmptyStringWhenConvertToDatabaseColumnIsCalledWithNull() {
    // GIVEN: nothing

    // WHEN
    String actual = uut.convertToDatabaseColumn(null);

    // THEN
    assertTrue(actual.isBlank());

    verifyNoInteractions(mapper);
  }

  @Test
  @DisplayName("Should throw an IllegalArgumentException when mapper throws a "
      + "JsonProcessingException.")
  void shouldThrowIllegalArgumentExceptionWhenMapperThrowsJsonProcessingException()
      throws JsonProcessingException {
    // GIVEN
    when(mapper.writeValueAsString(any())).thenThrow(toBeThrown);

    String expectedMessage = String.format(
        MapToJsonConverter.SERIALIZATION_EXCEPTION_MESSAGE_FORMAT,
        map);

    // WHEN
    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> uut.convertToDatabaseColumn(map));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
  }

  @Test
  @DisplayName("Should call mapper with expected Argument when convertToAttribute is called.")
  @SuppressWarnings("unchecked")
  void shouldCallMapperWithExpectedArgumentWhenConvertToAttributeIsCalled()
      throws JsonProcessingException {
    // GIVEN
    when(mapper.readValue(anyString(), any(Class.class))).thenReturn(map);

    // WHEN
    Map<String, Object> actual = uut.convertToEntityAttribute(dbData);

    // THEN
    assertThat(actual, sameInstance(map));

    verify(mapper).readValue(dbData, Map.class);
  }

  @Test
  @DisplayName("Should return empty map when convertToAttribute is called with empty string.")
  @SuppressWarnings("unchecked")
  void shouldReturnEmptyMapWhenConvertToAttributeIsCalledWithEmptyString()
      throws JsonProcessingException {
    // GIVEN
    when(mapper.readValue(anyString(), any(Class.class))).thenReturn(new HashMap<>());

    // WHENa?
    Map<String, Object> actual = uut.convertToEntityAttribute("");

    // THEN
    assertTrue(actual.isEmpty());

    verifyNoInteractions(mapper);
  }

  @Test
  @DisplayName("Should return empty map when convertToAttribute is called with null.")
  @SuppressWarnings("unchecked")
  void shouldReturnEmptyMapWhenConvertToAttributeIsCalledWithNull()
      throws JsonProcessingException {
    // GIVEN
    when(mapper.readValue(anyString(), any(Class.class))).thenReturn(new HashMap<>());

    // WHENa?
    Map<String, Object> actual = uut.convertToEntityAttribute(null);

    // THEN
    assertTrue(actual.isEmpty());

    verifyNoInteractions(mapper);
  }

  @Test
  @DisplayName("Should throw an IllegalStateException when mapper throws a "
      + "JsonProcessingException.")
  @SuppressWarnings("unchecked")
  void shouldThrowIllegalStateExceptionWhenMapperThrowsJsonProcessingException()
      throws JsonProcessingException {
    // GIVEN
    when(mapper.readValue(anyString(), any(Class.class))).thenThrow(toBeThrown);

    String expectedMessage = String.format(
        MapToJsonConverter.DESERIALIZATION_EXCEPTION_MESSAGE_FORMAT,
        dbData);

    // WHEN
    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> uut.convertToEntityAttribute(dbData));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
  }
}