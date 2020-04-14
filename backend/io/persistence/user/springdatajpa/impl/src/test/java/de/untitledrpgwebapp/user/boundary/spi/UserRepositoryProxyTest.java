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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.boundary.spi.boundary.JpaUserRepository;
import de.untitledrpgwebapp.user.boundary.spi.boundary.UserRepositoryProxy;
import de.untitledrpgwebapp.user.boundary.spi.mapper.UserMapper;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRepositoryProxyTest {

  final JpaUserRepository repository = mock(JpaUserRepository.class);
  final UserMapper mapper = mock(UserMapper.class);

  final UserRepositoryProxy uut = new UserRepositoryProxy(repository, mapper);

  @BeforeEach
  void setup() {
    when(mapper.entityToResponse(eq(USER_ENTITY_ONE))).thenReturn(USER_RESPONSE_ONE);
    when(mapper.entityToResponse(eq(USER_ENTITY_TWO))).thenReturn(USER_RESPONSE_TWO);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN:
    when(repository.findAll()).thenReturn(USER_ENTITIES);

    // WHEN
    Collection<UserResponse> actual = uut.findAll();

    // THEN
    assertThat(actual, hasSize(USER_NAMES.size()));
    assertThat(actual, containsInAnyOrder(USER_RESPONSES.toArray()));
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
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when save is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenSaveIsCalled() {
    // GIVEN
    when(repository.save(any())).thenReturn(USER_ENTITY_ONE);

    // WHEN
    UserResponse actual = uut.save(CreateUserRequest.builder().name(USER_ONE_NAME).build());

    // THEN
    assertThat(actual.getName(), is(USER_ONE_NAME));
  }
}