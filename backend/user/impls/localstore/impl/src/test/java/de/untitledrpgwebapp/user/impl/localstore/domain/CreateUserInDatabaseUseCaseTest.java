package de.untitledrpgwebapp.user.impl.localstore.domain;

import static de.untitledrpgwebapp.user.impl.localstore.testfixture.UserData.CORRELATION_ID;
import static de.untitledrpgwebapp.user.impl.localstore.testfixture.UserData.EMAIL_ONE;
import static de.untitledrpgwebapp.user.impl.localstore.testfixture.UserData.NAME_ONE;
import static de.untitledrpgwebapp.user.impl.localstore.testfixture.UserData.RESPONSE_ONE;
import static de.untitledrpgwebapp.user.impl.localstore.testfixture.UserData.TAG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.exception.LanguageNotFoundException;
import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.testfixture.LanguageData;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserInDatabaseUseCase unit")
class CreateUserInDatabaseUseCaseTest {

  private CreateUserRequest request;

  private UserRepository repository;
  private FindLanguageByTagUseCase findLanguageByTag;

  CreateAccountRequest createAccountRequest;
  private CreateAccountUseCase createAccount;

  private CreateUserInDatabaseUseCase uut;

  @BeforeEach
  void setup() {
    request = CreateUserRequest.builder()
        .preferredLanguageTag(TAG_ONE)
        .correlationId(CORRELATION_ID)
        .build();

    findLanguageByTag = mock(FindLanguageByTagUseCase.class);
    when(findLanguageByTag.execute(any()))
        .thenReturn(Optional.of(LanguageData.RESPONSE));

    createAccountRequest = CreateAccountRequest.builder().build();
    UserMapper mapper = mock(UserMapper.class);
    when(mapper.requestToRequest(any())).thenReturn(createAccountRequest);

    createAccount = mock(CreateAccountUseCase.class);
    repository = mock(UserRepository.class);
    when(repository.save(any())).thenReturn(RESPONSE_ONE);

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
    assertThat(actual, is(notNullValue()));
    assertThat(actual.getName(), is(NAME_ONE));
    assertThat(actual.getEmail(), is(EMAIL_ONE));
    assertThat(actual.getPreferredLanguageTag(), is(TAG_ONE));
    assertThat(actual.getCorrelationId(), is(CORRELATION_ID));
  }

  private void verifyFindLanguageByCalledWasCalledWithExpectedParameters() {
    verify(findLanguageByTag).execute(argThat(request -> {
      assertThat(request.getCorrelationId(), is(CORRELATION_ID));
      assertThat(request.getTag(), is(TAG_ONE));
      return true;
    }));
  }

  @Test
  @DisplayName("Should throw a LanguageNotFoundException if the preferred language is not found.")
  void shouldThrowLanguageNotFoundExceptionWhenLanguageIsNotFound() {
    // GIVEN
    when(findLanguageByTag.execute(any())).thenReturn(Optional.empty());

    String expectedMessage =
        String.format(LanguageNotFoundException.MESSAGE_FORMAT, TAG_ONE);

    // WHEN
    LanguageNotFoundException exception = assertThrows(
        LanguageNotFoundException.class,
        () -> uut.execute(request));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
    assertThat(exception.getCorrelationId(), is(CORRELATION_ID));
  }
}