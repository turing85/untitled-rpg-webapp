package de.untitledrpgwebapp.user.impl.localstore.domain.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByCodeUseCase;
import de.untitledrpgwebapp.language.exception.LanguageNotFoundException;
import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.UserRepository;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserEntity;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserInDatabaseUseCase unit")
class CreateUserInDatabaseUseCaseTest {

  private String name = "name";
  private String email = "email";
  private String password = "password";
  private String code = "code";
  private UUID correlationId = UUID.randomUUID();
  private CreateUserRequest request;

  private UserRepository repository;
  private FindLanguageByCodeUseCase findLanguageByCode;
  private CreateAccountUseCase createAccount;

  private CreateUserInDatabaseUseCase sut;

  @BeforeEach
  void setup() {
    request = CreateUserRequest.builder()
        .preferredLanguageCode(code)
        .correlationId(correlationId)
        .build();

    findLanguageByCode = mock(FindLanguageByCodeUseCase.class);
    when(findLanguageByCode.execute(any()))
        .thenReturn(Optional.of(LanguageResponse.builder().code(code).build()));

    CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
        .name(name)
        .email(email)
        .password(password)
        .build();
    UserMapper mapper = mock(UserMapper.class);
    when(mapper.requestToRequest(any())).thenReturn(createAccountRequest);
    createAccount = mock(CreateAccountUseCase.class);

    UserResponse response = UserResponse.builder().build();
    when(mapper.entityToRequest(any())).thenReturn(response);
    UserEntity entity = UserEntity.builder().build();
    repository = mock(UserRepository.class);
    when(repository.save(any())).thenReturn(Optional.of(entity));

    sut = new CreateUserInDatabaseUseCase(mapper, repository, findLanguageByCode, createAccount);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters when everything is okay")
  void shouldCallDependenciesWithExpectedParametersWhenEverythingIsOkay() {
    // GIVEN: defaults

    // WHEN
    UserResponse actual = sut.execute(request);

    // THEN
    assertNotNull(actual);
    assertEquals(correlationId, actual.getCorrelationId());

    verify(findLanguageByCode).execute(argThat(request -> {
      assertEquals(correlationId, request.getCorrelationId());
      assertEquals(code, request.getCode());
      return true;
    }));
    verify(createAccount).execute(argThat(accountRequest -> {
      assertEquals(name, accountRequest.getName());
      assertEquals(email, accountRequest.getEmail());
      assertEquals(password, accountRequest.getPassword());
      assertEquals(correlationId, accountRequest.getCorrelationId());
      return true;
    }));
    verify(repository).save(request);
  }

  @Test
  @DisplayName("Should throw a LanguageNotFoundException if the language is not found")
  void shouldThrowLanguageNotFoundExceptionWhenLanguageIsNotFound() {
    // GIVEN
    when(findLanguageByCode.execute(any())).thenReturn(Optional.empty());

    String expectedMessage =
        String.format(LanguageNotFoundException.MESSAGE_FORMAT, code);
    // WHEN
    LanguageNotFoundException exception = assertThrows(
        LanguageNotFoundException.class,
        () -> sut.execute(request));

    // THEN
    assertEquals(expectedMessage, exception.getMessage());
    assertEquals(correlationId, exception.getCorrelationId());
  }

}