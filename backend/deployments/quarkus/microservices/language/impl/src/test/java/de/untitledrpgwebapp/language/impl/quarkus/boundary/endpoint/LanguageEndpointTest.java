package de.untitledrpgwebapp.language.impl.quarkus.boundary.endpoint;

import static de.untitledrpgwebapp.language.impl.quarkus.boundary.testfixture.LanguageDtoFixture.DTOS;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_RESPONSES;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TAGS;
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
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.CreateLanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.dto.LanguageDto;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LanguageEndpoint unit")
class LanguageEndpointTest {

  private final FindAllLanguagesUseCase findAllLanguages= mock(FindAllLanguagesUseCase.class);
  private final FindLanguageByTagUseCase findLanguage = mock(FindLanguageByTagUseCase.class);
  private final CreateLanguageUseCase createLanguage = mock(CreateLanguageUseCase.class);
  private final LanguageMapper mapper = mock(LanguageMapper.class);

  private final LanguageEndpoint uut  = new LanguageEndpoint(findAllLanguages, findLanguage, createLanguage, mapper);

  @BeforeEach
  void setup() {
    when(mapper.responseToDto(any())).thenReturn(LanguageDto.builder().tag(LANGUAGE_ONE_TAG).build());
  }

  @Test
  @DisplayName("Should call findAllLanguages with the expected parameters and return the expected "
      + "response object")
  void shouldCallFindAllLanguagesWithExpectedParameterAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN
    when(findAllLanguages.execute(any())).thenReturn(LANGUAGE_RESPONSES);
    when(mapper.responsesToDtos(anyList())).thenReturn(DTOS);

    // WHEN
    Response response = uut.findAll(CORRELATION_ID);

    // THEN
    assertCollectionResponseIsAsExpected(response);

    verify(findAllLanguages).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
    verify(mapper).responsesToDtos(LANGUAGE_RESPONSES);
  }

  @Test
  void shouldCallFindLanguageWithExpectedParameterAndReturnExpectedResultWhenFindByTagIsCalled() {
    // GIVEN
    when(findLanguage.execute(any())).thenReturn(Optional.of(LANGUAGE_ONE_RESPONSE));

    // WHEN
    Response response = uut.findByTag(LANGUAGE_ONE_TAG, CORRELATION_ID);

    // THEN
    assertResponseIsAsExpected(response);

    verify(findLanguage).execute(argThat(r -> {
      assertThat(r.getCorrelationId(), is(CORRELATION_ID));
      return true;
    }));
  }

  @Test
  void shouldCallCreateLanguageWithExpectedParameterAndReturnExpectedResultWhenCreateLanguageIsCalled() {
    // GIVEN:
    CreateLanguageRequest request = CreateLanguageRequest.builder().tag(LANGUAGE_ONE_TAG).build();
    when(mapper.dtoToRequest(any(), any())).thenReturn(request);
    when(createLanguage.execute(any())).thenReturn(LANGUAGE_ONE_RESPONSE);

    // WHEN
    Response response = uut.createLanguage(new CreateLanguageDto().setTag(LANGUAGE_ONE_TAG), CORRELATION_ID);

    // THEN
    assertCreateLanguageResponseIsAsExpected(response);

    verify(createLanguage).execute(request);
  }

  private void assertResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.OK);
  }

  private void assertResponseIsAsExpected(Response response, Status status) {
    assertThat(response.getStatus(), is(status.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(LanguageDto.class));
    LanguageDto actual = (LanguageDto) entity;
    assertThat(actual.getTag(), is(LANGUAGE_ONE_TAG));
  }

  private void assertCollectionResponseIsAsExpected(Response response) {
    assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
    List<Object> correlationIdHeaders =
        response.getHeaders().get(StaticConfig.CORRELATION_ID_HEADER_KEY);
    assertThat(correlationIdHeaders, hasSize(1));
    assertThat(correlationIdHeaders.get(0), is(CORRELATION_ID));
    Object entity = response.getEntity();
    assertThat(entity, instanceOf(List.class));
    @SuppressWarnings("unchecked")
    List<LanguageDto> actual = (List<LanguageDto>) entity;
    assertThat(actual, hasSize(LANGUAGE_RESPONSES.size()));
    assertThat(
        actual.stream().map(LanguageDto::getTag).collect(Collectors.toList()),
        containsInAnyOrder(LANGUAGE_TAGS.toArray()));
  }

  private void assertCreateLanguageResponseIsAsExpected(Response response) {
    assertResponseIsAsExpected(response, Status.CREATED);
    List<Object> locationHeaders = response.getHeaders().get("LOCATION");
    assertThat(locationHeaders, hasSize(1));
    assertThat(
        locationHeaders.get(0),
        is(URI.create(String.format(LanguageEndpoint.GET_ONE_PATH_TEMPLATE, LANGUAGE_ONE_TAG))));
  }
}