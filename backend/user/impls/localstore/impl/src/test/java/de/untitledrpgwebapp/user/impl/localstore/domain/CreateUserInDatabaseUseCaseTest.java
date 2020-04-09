package de.untitledrpgwebapp.user.impl.localstore.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.exception.LanguageNotFoundException;
import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserInDatabaseUseCase unit")
class CreateUserInDatabaseUseCaseTest {

  private final String name = "name";
  private final String email = "email";
  private final String tag = "tag";
  private UUID correlationId = UUID.randomUUID();
  private CreateUserRequest request;

  private UserRepository repository;
  private FindLanguageByTagUseCase findLanguageByTag;

  CreateAccountRequest createAccountRequest;
  private CreateAccountUseCase createAccount;

  private CreateUserInDatabaseUseCase uut;

  @BeforeEach
  void setup() {
    request = CreateUserRequest.builder()
        .preferredLanguageTag(tag)
        .correlationId(correlationId)
        .build();
    UserResponse response = UserResponse.builder()
        .name(name)
        .email(email)
        .preferredLanguageTag(tag)
        .build();

    findLanguageByTag = mock(FindLanguageByTagUseCase.class);
    when(findLanguageByTag.execute(any()))
        .thenReturn(Optional.of(LanguageResponse.builder().tag(tag).build()));

    createAccountRequest = CreateAccountRequest.builder().build();
    UserMapper mapper = mock(UserMapper.class);
    when(mapper.requestToRequest(any())).thenReturn(createAccountRequest);
    createAccount = mock(CreateAccountUseCase.class);
    repository = mock(UserRepository.class);
    when(repository.save(any())).thenReturn(response);

    uut = new CreateUserInDatabaseUseCase(mapper, repository, findLanguageByTag, createAccount);
  }

  @Test
  @DisplayName("Should call all dependencies with the expected parameters and return the expected "
      + "response when everything is ok.")
  void shouldCallDependenciesWithExpectedParametersWhenEverythingIsOkay() {
    // GIVEN: defaults

    // WHEN
    UserResponse actual = uut.execute(request);

    // THEN
    assertThatActualIsAsExpected(actual);

    verifyFindLanguageByCalledWasCalledWithExpectedParameters();
    verify(createAccount).execute(createAccountRequest);
    verify(repository).save(request);
  }

  private void assertThatActualIsAsExpected(UserResponse actual) {
    assertNotNull(actual);
    assertEquals(name, actual.getName());
    assertEquals(email, actual.getEmail());
    assertEquals(tag, actual.getPreferredLanguageTag());
    assertEquals(correlationId, actual.getCorrelationId());
  }

  private void verifyFindLanguageByCalledWasCalledWithExpectedParameters() {
    verify(findLanguageByTag).execute(argThat(request -> {
      assertEquals(correlationId, request.getCorrelationId());
      assertEquals(tag, request.getTag());
      return true;
    }));
  }

  @Test
  @DisplayName("Should throw a LanguageNotFoundException if the preferred language is not found.")
  void shouldThrowLanguageNotFoundExceptionWhenLanguageIsNotFound() {
    // GIVEN
    when(findLanguageByTag.execute(any())).thenReturn(Optional.empty());

    String expectedMessage =
        String.format(LanguageNotFoundException.MESSAGE_FORMAT, tag);

    // WHEN
    LanguageNotFoundException exception = assertThrows(
        LanguageNotFoundException.class,
        () -> uut.execute(request));

    // THEN
    assertEquals(expectedMessage, exception.getMessage());
    assertEquals(correlationId, exception.getCorrelationId());
  }
}