package de.untitledrpgwebapp.language.boundary;

import static de.untitledrpgwebapp.language.boundary.testfixture.LanguageEntityFixture.LANGUAGE_ENTITES;
import static de.untitledrpgwebapp.language.boundary.testfixture.LanguageEntityFixture.LANGUAGE_ONE_ENTITY;
import static de.untitledrpgwebapp.language.boundary.testfixture.LanguageEntityFixture.LANGUAGE_TWO_ENTITY;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_RESPONSES;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TAGS;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_TWO_RESPONSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.boundary.FindAllPaged;
import de.untitledrpgwebapp.common.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.language.boundary.entity.JpaLanguageEntity;
import de.untitledrpgwebapp.language.boundary.mapper.JpaLanguageMapper;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for LanguageRepositoryProxy unit.")
class JpaLanguageDaoTest {

  final JpaLanguageRepository repository = mock(JpaLanguageRepository.class);
  final JpaLanguageMapper mapper = mock(JpaLanguageMapper.class);

  @SuppressWarnings("unchecked")
  final FindAllPaged<JpaLanguageEntity> findAllPaged = mock(FindAllPaged.class);

  final JpaLanguageDao uut =
      new JpaLanguageDao(repository, mapper, findAllPaged);

  @BeforeEach
  void setup() {
    when(mapper.entityToResponse(eq(LANGUAGE_ONE_ENTITY)))
        .thenReturn(LANGUAGE_ONE_RESPONSE);
    when(mapper.entityToResponse(eq(LANGUAGE_TWO_ENTITY)))
        .thenReturn(LANGUAGE_TWO_RESPONSE);
  }

  @Test
  @DisplayName("Should construct object with expected fields when constructor is called.")
  void shouldConstructObjectWithExpectedFieldsWhenConstructorIsCalled() {
    // GIVEN
    EntityManager manager = mock(EntityManager.class);

    // WHEN
    JpaLanguageDao actual = new JpaLanguageDao(repository, mapper, manager);

    // THEN
    assertThat(actual.getRepository(), is(repository));
    assertThat(actual.getMapper(), is(mapper));
    assertThat(actual.getFindAllPaged(), is(notNullValue()));
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN
    when(findAllPaged.findAll(any(PageAndSortConfig.class))).thenReturn(LANGUAGE_ENTITES);
    when(mapper.entitiesToResponses(anyList())).thenReturn(LANGUAGE_RESPONSES);

    PageAndSortConfig config = mock(PageAndSortConfig.class);

    // WHEN
    Collection<LanguageResponse> actual = uut.findAll(config);

    // THEN
    assertThat(actual, hasSize(LANGUAGE_TAGS.size()));
    assertThat(actual, containsInAnyOrder(LANGUAGE_RESPONSES.toArray()));

    verify(findAllPaged).findAll(config);
    verify(mapper).entitiesToResponses(LANGUAGE_ENTITES);
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