package de.untitledrpgwebapp.user.impl.localstore.domain;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_EMAIL;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_ONE_NAME;
import static de.untitledrpgwebapp.user.testfixture.UserFixture.USER_RESPONSE_ONE;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateUserInDatabaseUseCase unit")
class CreateUserInDatabaseUseCaseTest {

  private final CreateUserRequest request = CreateUserRequest.builder()
      .preferredLanguageTag(LANGUAGE_ONE_TAG)
      .correlationId(CORRELATION_ID)
      .build();

  private final UserRepository repository = mock(UserRepository.class);
  private final FindLanguageByTagUseCase findLanguageByTag = mock(FindLanguageByTagUseCase.class);

  private final CreateAccountRequest createAccountRequest = CreateAccountRequest.builder().build();
  private final CreateAccountUseCase createAccount = mock(CreateAccountUseCase.class);
  private final UserMapper mapper = mock(UserMapper.class);

  private final CreateUserInDatabaseUseCase uut
      = new CreateUserInDatabaseUseCase(mapper, repository, findLanguageByTag, createAccount);

  @BeforeEach
  void setup() {
    when(findLanguageByTag.execute(any())).thenReturn(Optional.of(LANGUAGE_ONE_RESPONSE));

    when(mapper.requestToRequest(any())).thenReturn(createAccountRequest);

    when(repository.save(any())).thenReturn(USER_RESPONSE_ONE);
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

  @Test
  @DisplayName("Should throw a LanguageNotFoundException if the preferred language is not found.")
  void shouldThrowLanguageNotFoundExceptionWhenLanguageIsNotFound() {
    // GIVEN
    when(findLanguageByTag.execute(any())).thenReturn(Optional.empty());

    String expectedMessage =
        String.format(LanguageNotFoundException.MESSAGE_FORMAT, LANGUAGE_ONE_TAG);

    // WHEN
    LanguageNotFoundException exception = assertThrows(
        LanguageNotFoundException.class,
        () -> uut.execute(request));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
    assertThat(exception.getCorrelationId(), is(CORRELATION_ID));
  }

  private void assertThatActualIsAsExpected(UserResponse actual) {
    assertThat(actual, is(notNullValue()));
    assertThat(actual.getName(), is(USER_ONE_NAME));
    assertThat(actual.getEmail(), is(USER_ONE_EMAIL));
    assertThat(actual.getPreferredLanguageTag(), is(LANGUAGE_ONE_TAG));
    assertThat(actual.getCorrelationId(), is(CORRELATION_ID));
  }

  private void verifyFindLanguageByCalledWasCalledWithExpectedParameters() {
    verify(findLanguageByTag).execute(argThat(request -> {
      assertThat(request.getCorrelationId(), is(CORRELATION_ID));
      assertThat(request.getTag(), is(LANGUAGE_ONE_TAG));
      return true;
    }));
  }
}