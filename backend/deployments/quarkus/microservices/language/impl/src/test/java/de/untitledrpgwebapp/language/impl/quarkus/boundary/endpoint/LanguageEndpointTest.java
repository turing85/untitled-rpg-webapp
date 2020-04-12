package de.untitledrpgwebapp.language.impl.quarkus.boundary.endpoint;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.impl.quarkus.configuration.StaticConfig;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LanguageEndpoint unit")
class LanguageEndpointTest {

  private final UUID correlationId = UUID.randomUUID();
  private final String languageOneTag = "languageOneTag";
  private final String languageTwoTag = "languageTwoTag";
  private final List<String> languageTags = List.of(languageOneTag, languageTwoTag);

  private final List<LanguageResponse> found = List.of(
      LanguageResponse.builder().tag(languageOneTag).build(),
      LanguageResponse.builder().tag(languageTwoTag).build());

  private final List<LanguageDto> dtos = List.of(
      LanguageDto.builder().tag(languageOneTag).build(),
      LanguageDto.builder().tag(languageTwoTag).build());

  private FindAllLanguagesUseCase findAllLanguages;
  private FindLanguageByTagUseCase findLanguage;
  private CreateLanguageUseCase createLanguage;
  private LanguageMapper mapper;

  private LanguageEndpoint uut;

  @BeforeEach
  void setup() {
    findAllLanguages = mock(FindAllLanguagesUseCase.class);
    findLanguage = mock(FindLanguageByTagUseCase.class);

    createLanguage = mock(CreateLanguageUseCase.class);
    when(findAllLanguages.execute(any())).thenReturn(found);

    findLanguage = mock(FindLanguageByTagUseCase.class);
    when(findLanguage.execute(any()))
        .thenReturn(Optional.of(LanguageResponse.builder().tag(languageOneTag).build()));
    when(createLanguage.execute(any()))
        .thenReturn(LanguageResponse.builder().tag(languageOneTag).build());

    mapper = mock(LanguageMapper.class);
    when(mapper.responsesToDtos(anyList())).thenReturn(dtos);
    when(mapper.responseToDto(any())).thenReturn(LanguageDto.builder().tag(languageOneTag).build());
    when(mapper.dtoToRequest(any()))
        .thenReturn(CreateLanguageRequest.builder().tag(languageOneTag).build());

    uut = new LanguageEndpoint(findAllLanguages, findLanguage, createLanguage, mapper);
  }

  @Test
  @DisplayName("Should call findAllLanguages with the expected parameters and return the expected "
      + "response object")
  void shouldCallFindAllLanguagesWithExpectedParameterAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response = uut.findAll(correlationId);

    // THEN
    assertCollectionResponseIsAsExpected(response);

    verify(findAllLanguages).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(correlationId));
      return true;
    }));
    verify(mapper).responsesToDtos(found);
  }

  @Test
  void shouldCallFindLanguageWithExpectedParameterAndReturnExpectedREsultWhenFindByTagIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response = uut.findByTag(languageOneTag, correlationId);

    // THEN
    assertResponseIsAsExpected(response);

    verify(findLanguage).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(correlationId));
      return true;
    }));
  }

  @Test
  void shouldCallCreateLanguageWithExpectedParameterAndReturnExpectedResultWhenCreateLanguageIsCalled() {
    // GIVEN: defaults

    // WHEN
    Response response =
        uut.createLanguage(CreateLanguageDto.builder().tag(languageOneTag).build(), correlationId);

    // THEN
    assertCreateLanguageResponseIsAsExpected(response);
  }

  private void assertResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.OK);
  }

  private void assertResponseIsAsExpected(Response response, Status status) {
    assertThat(response.getStatus(), is(status.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(correlationId));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(LanguageDto.class));
    LanguageDto actual = (LanguageDto) entity;
    assertThat(actual.getTag(), is(languageOneTag));
  }

  private void assertCollectionResponseIsAsExpected(Response response) {
    assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(correlationId));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(List.class));
    @SuppressWarnings("unchecked")
    List<LanguageDto> actual = (List<LanguageDto>) entity;
    assertThat(actual, hasSize(found.size()));
    assertThat(
        actual.stream().map(LanguageDto::getTag).collect(Collectors.toList()),
        containsInAnyOrder(languageTags.toArray()));
  }

  private void assertCreateLanguageResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.CREATED);
    List<Object> locationHeaders = response.getHeaders().get("LOCATION");
    assertThat(locationHeaders, hasSize(1));
    assertThat(
        locationHeaders.get(0),
        is(URI.create(String.format(LanguageEndpoint.GET_ONE_PATH_TEMPLATE, languageOneTag))));
  }
}