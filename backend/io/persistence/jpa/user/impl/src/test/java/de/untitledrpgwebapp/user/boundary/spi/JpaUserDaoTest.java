package de.untitledrpgwebapp.user.boundary.spi;

import static de.untitledrpgwebapp.user.boundary.testfixture.JpaUserEntityFixture.USER_ENTITIES;
import static de.untitledrpgwebapp.user.boundary.testfixture.JpaUserEntityFixture.USER_ENTITY_ONE;
import static de.untitledrpgwebapp.user.boundary.testfixture.JpaUserEntityFixture.USER_ENTITY_TWO;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_NAMES;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSES;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSE_ONE;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSE_TWO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.boundary.persistence.FindAllPaged;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.boundary.spi.entity.JpaUserEntity;
import de.untitledrpgwebapp.user.boundary.spi.mapper.UserMapper;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for UserRepositoryProxy unit.")
class JpaUserDaoTest {

  final JpaUserRepository repository = mock(JpaUserRepository.class);
  final UserMapper mapper = mock(UserMapper.class);

  @SuppressWarnings("unchecked")
  final FindAllPaged<JpaUserEntity> findAllPaged = mock(FindAllPaged.class);

  final JpaUserDao uut = new JpaUserDao(repository, mapper, findAllPaged);

  @BeforeEach
  void setup() {
    when(mapper.entityToResponse(eq(USER_ENTITY_ONE))).thenReturn(USER_RESPONSE_ONE);
    when(mapper.entityToResponse(eq(USER_ENTITY_TWO))).thenReturn(USER_RESPONSE_TWO);
  }

  @Test
  @DisplayName("Should construct object with expected fields when constructor is called.")
  void shouldConstructObjectWithExpectedFieldsWhenConstructorIsCalled() {
    // GIVEN
    EntityManager manager = mock(EntityManager.class);

    // WHEN
    JpaUserDao actual = new JpaUserDao(repository, mapper, manager);

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
    when(findAllPaged.findAll(any(PageAndSortConfig.class))).thenReturn(USER_ENTITIES);
    when(mapper.entitiesToResponses(anyList())).thenReturn(USER_RESPONSES);

    PageAndSortConfig config = mock(PageAndSortConfig.class);
    // WHEN
    Collection<UserResponse> actual = uut.findAll(config);

    // THEN
    assertThat(actual, hasSize(USER_NAMES.size()));
    assertThat(actual, containsInAnyOrder(USER_RESPONSES.toArray()));

    verify(findAllPaged).findAll(config);
    verify(mapper).entitiesToResponses(USER_ENTITIES);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findByName is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindByNameIsCalled() {
    // GIVEN: defaults
    when(repository.findByName(anyString())).thenReturn(Optional.of(USER_ENTITY_ONE));

    // WHEN:
    Optional<UserResponse> fetched = uut.findByName(USER_ONE_NAME);

    // THEN
    assertTrue(fetched.isPresent());
    UserResponse actual = fetched.get();
    assertThat(actual.getName(), is(USER_ONE_NAME));

    verify(repository).findByName(USER_ONE_NAME);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when save is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenSaveIsCalled() {
    // GIVEN
    when(mapper.requestToEntity(any())).thenReturn(USER_ENTITY_ONE);
    when(repository.save(any())).thenReturn(USER_ENTITY_ONE);

    // WHEN
    UserResponse actual = uut.save(CreateUserRequest.builder().name(USER_ONE_NAME).build());

    // THEN
    assertThat(actual.getName(), is(USER_ONE_NAME));

    verify(repository).save(USER_ENTITY_ONE);
  }
}