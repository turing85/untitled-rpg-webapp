package de.untitledrpgwebapp.language.impl.quarkus.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.quarkus.boundary.mapper.LanguageMapper;
import de.untitledrpgwebapp.language.impl.quarkus.entity.JpaLanguageEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LanguageRepositoryProxy unit")
class LanguageRepositoryProxyTest {

  private final String languageOneTag = "languageOneTag";
  private final String languageTwoTag = "languageTwoTag";
  private final List<String> languageTags = List.of(languageOneTag, languageTwoTag);

  JpaLanguageRepository repository;
  LanguageMapper mapper;

  LanguageRepositoryProxy uut;

  @BeforeEach
  void setup() {
    repository = mock(JpaLanguageRepository.class);
    when(repository.findAll()).thenReturn(languageTags.stream()
        .map(tag -> JpaLanguageEntity.builder().tag(tag).build())
        .collect(Collectors.toList()));
    when(repository.findByTag(anyString()))
        .thenReturn(Optional.of(JpaLanguageEntity.builder().tag(languageOneTag).build()));
    when(repository.save(any()))
        .thenReturn(JpaLanguageEntity.builder().tag(languageOneTag).build());

    mapper = mock(LanguageMapper.class);
    when(mapper.entityToResponse(any()))
        .thenAnswer(invocation -> LanguageResponse.builder()
            .tag(invocation.getArgument(0, JpaLanguageEntity.class).getTag())
            .build());

    uut = new LanguageRepositoryProxy(repository, mapper);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN: defaults

    // WHEN
    Collection<LanguageResponse> actual = uut.findAll();

    // THEN
    assertThat(actual, hasSize(languageTags.size()));
    assertThat(
        actual.stream().map(LanguageResponse::getTag).collect(Collectors.toList()),
        containsInAnyOrder(languageTags.toArray()));
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findByTag is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindByTagIsCalled() {
    // GIVEN: defaults

    // WHEN:
    Optional<LanguageResponse> actual = uut.findByTag(languageOneTag);

    // THEN
    assertTrue(actual.isPresent());
    assertThat(actual.get().getTag(), is(languageOneTag));
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when save is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenSaveIsCalled() {
    // GIVEN: defaults

    // WHEN
    LanguageResponse actual = uut.save(CreateLanguageRequest.builder().tag(languageOneTag).build());

    // THEN
    assertThat(actual.getTag(), is(languageOneTag));
  }
}