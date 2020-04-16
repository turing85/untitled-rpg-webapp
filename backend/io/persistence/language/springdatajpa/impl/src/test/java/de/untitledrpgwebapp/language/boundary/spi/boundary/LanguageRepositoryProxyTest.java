package de.untitledrpgwebapp.language.boundary.spi.boundary;

import static de.untitledrpgwebapp.impl.quarkus.testfixture.PageConfigDtoFixture.PAGE_CONFIG_DTO;
import static de.untitledrpgwebapp.language.boundary.spi.testfixture.LanguageEntityFixture.LANGUAGE_ENTITES;
import static de.untitledrpgwebapp.language.boundary.spi.testfixture.LanguageEntityFixture.LANGUAGE_ONE_ENTITY;
import static de.untitledrpgwebapp.language.boundary.spi.testfixture.LanguageEntityFixture.LANGUAGE_TWO_ENTITY;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_RESPONSES;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TAGS;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TWO_RESPONSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.impl.quarkus.boundary.mapper.PageRequestMapper;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.boundary.spi.entity.JpaLanguageEntity;
import de.untitledrpgwebapp.language.boundary.spi.mapper.LanguageMapper;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DisplayName("Tests for LanguageRepositoryProxy unit.")
class LanguageRepositoryProxyTest {

  final JpaLanguageRepository repository = mock(JpaLanguageRepository.class);
  final LanguageMapper languageMapper = mock(LanguageMapper.class);
  final PageRequestMapper pageableMapper = mock(PageRequestMapper.class);

  final LanguageRepositoryProxy uut =
      new LanguageRepositoryProxy(repository, languageMapper, pageableMapper);

  @BeforeEach
  void setup() {
    when(languageMapper.entityToResponse(eq(LANGUAGE_ONE_ENTITY)))
        .thenReturn(LANGUAGE_ONE_RESPONSE);
    when(languageMapper.entityToResponse(eq(LANGUAGE_TWO_ENTITY)))
        .thenReturn(LANGUAGE_TWO_RESPONSE);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN
    @SuppressWarnings("unchecked")
    Page<JpaLanguageEntity> page = (Page<JpaLanguageEntity>) mock(Page.class);

    when(page.spliterator()).thenReturn(LANGUAGE_ENTITES.spliterator());
    when(repository.findAll(any(Pageable.class))).thenReturn(page);

    PageRequest pageable = mock(PageRequest.class);
    when(pageableMapper.configToPageable(any())).thenReturn(pageable);

    // WHEN
    Collection<LanguageResponse> actual = uut.findAll(PAGE_CONFIG_DTO);

    // THEN
    assertThat(actual, hasSize(LANGUAGE_TAGS.size()));
    assertThat(actual, containsInAnyOrder(LANGUAGE_RESPONSES.toArray()));

    verify(pageableMapper).configToPageable(PAGE_CONFIG_DTO);
    verify(repository).findAll(pageable);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findByTag is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindByTagIsCalled() {
    // GIVEN
    when(repository.findByTag(anyString())).thenReturn(Optional.of(LANGUAGE_ONE_ENTITY));

    // WHEN:
    Optional<LanguageResponse> fetched = uut.findByTag(LANGUAGE_ONE_TAG);

    // THEN
    assertTrue(fetched.isPresent());
    assertThat(fetched.get().getTag(), is(LANGUAGE_ONE_TAG));
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when save is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenSaveIsCalled() {
    // GIVEN
    when(repository.save(any())).thenReturn(LANGUAGE_ONE_ENTITY);

    // WHEN
    LanguageResponse actual =
        uut.save(CreateLanguageRequest.builder().tag(LANGUAGE_ONE_TAG).build());

    // THEN
    assertThat(actual.getTag(), is(LANGUAGE_ONE_TAG));
  }
}