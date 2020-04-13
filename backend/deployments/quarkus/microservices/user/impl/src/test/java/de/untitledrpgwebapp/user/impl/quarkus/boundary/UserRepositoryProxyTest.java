package de.untitledrpgwebapp.user.impl.quarkus.boundary;

import static de.untitledrpgwebapp.user.impl.quarkus.testfixture.UserData.USER_NAMES;
import static de.untitledrpgwebapp.user.impl.quarkus.testfixture.UserData.USER_ONE_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.quarkus.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.quarkus.entity.JpaUserEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for UserRepositoryProxy unit")
class UserRepositoryProxyTest {

  JpaUserRepository repository;
  UserMapper mapper;

  UserRepositoryProxy uut;

  @BeforeEach
  void setup() {
    repository = mock(JpaUserRepository.class);
    when(repository.findAll()).thenReturn(USER_NAMES.stream()
        .map(name -> JpaUserEntity.builder().name(name).build())
        .collect(Collectors.toList()));
    when(repository.findByName(anyString()))
        .thenReturn(Optional.of(JpaUserEntity.builder().name(USER_ONE_NAME).build()));
    when(repository.save(any()))
        .thenReturn(JpaUserEntity.builder().name(USER_ONE_NAME).build());

    mapper = mock(UserMapper.class);
    when(mapper.entityToResponse(any()))
        .thenAnswer(invocation -> UserResponse.builder()
            .name(invocation.getArgument(0, JpaUserEntity.class).getName())
            .build());

    uut = new UserRepositoryProxy(repository, mapper);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findAll is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindAllIsCalled() {
    // GIVEN: defaults

    // WHEN
    Collection<UserResponse> actual = uut.findAll();

    // THEN
    assertThat(actual, hasSize(USER_NAMES.size()));
    assertThat(
        actual.stream().map(UserResponse::getName).collect(Collectors.toList()),
        containsInAnyOrder(USER_NAMES.toArray()));
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when findByName is called.")
  void shouldCallDependenciesWithExpectedParametersAndReturnExpectedResultWhenFindByNameIsCalled() {
    // GIVEN: defaults

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
    // GIVEN: defaults

    // WHEN
    UserResponse actual = uut.save(CreateUserRequest.builder().name(USER_ONE_NAME).build());

    // THEN
    assertThat(actual.getName(), is(USER_ONE_NAME));
  }
}