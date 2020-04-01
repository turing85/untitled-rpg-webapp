package de.untitledrpgwebapp.domain.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.boundary.auth.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.language.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.auth.CreateAccountUseCase;
import de.untitledrpgwebapp.domain.language.FindLanguageByCodeUseCase;
import de.untitledrpgwebapp.exception.LanguageNotFoundException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserInDatabaseUseCase unit")
class CreateUserInDatabaseUseCaseTest {

  private UserMapper userMapper;
  private UserRepository userRepository;
  private FindLanguageByCodeUseCase findLanguageByCode;
  private CreateAccountUseCase createAccount;
  private Supplier<CreateAccountRequest> createAccountRequestSupplier;

  private String preferredLanguageCode = "preferredLanguageCode";
  private UUID correlationId = UUID.randomUUID();
  private CreateUserRequest createUserRequest;
  private CreateAccountRequest createAccountRequest;
  private UserResponseBuilder userResponseBuilder;
  private UserBuilder userBuilder;
  private LanguageResponseBuilder languageResponseBuilder;

  private CreateUserInDatabaseUseCase sut;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setup() {
    userResponseBuilder = mock(UserResponseBuilder.class);
    createUserRequest = mock(CreateUserRequest.class);
    when(createUserRequest.getCorrelationId()).thenReturn(correlationId);
    when(createUserRequest.getPreferredLanguageCode()).thenReturn(preferredLanguageCode);
    when(createUserRequest.getUserResponseBuilder()).thenReturn(userResponseBuilder);

    languageResponseBuilder = mock(LanguageResponseBuilder.class);
    when(languageResponseBuilder.getCode()).thenReturn(preferredLanguageCode);
    findLanguageByCode = mock(FindLanguageByCodeUseCase.class);
    when(findLanguageByCode.execute(any(FindLanguageByCodeRequest.class)))
        .thenReturn(Optional.of(new TestLanguageResponseBuilder()));
    when(findLanguageByCode.execute(any(FindLanguageByCodeRequest.class)))
        .thenReturn(Optional.of(languageResponseBuilder));

    createAccountRequest = mock(CreateAccountRequest.class);
    when(createAccountRequest.setCorrelationId(any(UUID.class))).thenReturn(createAccountRequest);
    userMapper = mock(UserMapper.class);
    when(userMapper.requestToRequest(
        any(CreateUserRequest.class),
        any(CreateAccountRequest.class))
    ).thenReturn(createAccountRequest);
    createAccountRequestSupplier = (Supplier<CreateAccountRequest>) mock(Supplier.class);
    when(createAccountRequestSupplier.get()).thenReturn(createAccountRequest);
    createAccount = mock(CreateAccountUseCase.class);

    when(userMapper.requestToRequest(any(UserBuilder.class), any(UserResponseBuilder.class)))
        .thenReturn(userResponseBuilder);
    userBuilder = mock(UserBuilder.class);
    when(userBuilder.setCorrelationId(any(UUID.class))).thenReturn(userBuilder);
    userRepository = mock(UserRepository.class);
    when(userRepository.save(any(CreateUserRequest.class)))
        .thenReturn(Optional.of(userBuilder));

    sut = new CreateUserInDatabaseUseCase(
        userMapper,
        userRepository,
        findLanguageByCode,
        createAccount,
        createAccountRequestSupplier
    );
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters when everything is okay")
  void shouldCallDependenciesWithExpectedParametersWhenEverythingIsOkay() {
    // GIVEN: defaults

    // WHEN
    UserResponseBuilder actual = sut.execute(createUserRequest);

    // THEN
    assertSame(userResponseBuilder, actual);

    verify(findLanguageByCode).execute(argThat(request -> {
      assertEquals(correlationId, request.getCorrelationId());
      assertEquals(preferredLanguageCode, request.getCode());
      return true;
    }));
    verify(createAccountRequest).setCorrelationId(correlationId);
    verify(createAccount).execute(same(createAccountRequest));
    verify(userRepository).save(createUserRequest);
    verify(userBuilder).setCorrelationId(correlationId);
  }

  @Test
  @DisplayName("Should throw a LanguageNotFoundException if the language is not found")
  void shouldThrowLanguageNotFoundExceptionWhenLanguageIsNotFound() {
    // GIVEN
    UserResponseBuilder expected = userResponseBuilder;
    when(findLanguageByCode.execute(any(FindLanguageByCodeRequest.class)))
        .thenReturn(Optional.empty());

    String expectedMessage =
        String.format(LanguageNotFoundException.MESSAGE_FORMAT, preferredLanguageCode);
    // WHEN
    LanguageNotFoundException e = assertThrows(
        LanguageNotFoundException.class,
        () -> sut.execute(createUserRequest));

    // THEN
    assertEquals(expectedMessage, e.getMessage());
    assertEquals(correlationId, e.getCorrelationId());
  }

  private static class TestLanguageResponseBuilder implements LanguageResponseBuilder {

    @Override
    public String getCode() {
      return null;
    }

    @Override
    public LanguageResponseBuilder setCode(String code) {
      return null;
    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public LanguageResponseBuilder setName(String name) {
      return null;
    }

    @Override
    public UUID getCorrelationId() {
      return null;
    }

    @Override
    public LanguageResponseBuilder setCorrelationId(UUID correlationId) {
      return null;
    }
  }

}