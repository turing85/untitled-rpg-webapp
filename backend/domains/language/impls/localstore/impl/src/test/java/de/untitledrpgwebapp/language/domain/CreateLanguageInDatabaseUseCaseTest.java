package de.untitledrpgwebapp.language.domain;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.domain.exception.EntityAlreadyExistsException;
import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.boundary.request.CreateLanguageRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for CreateLanguageInDatabaseUseCase unit.")
class CreateLanguageInDatabaseUseCaseTest {

  private final CreateLanguageRequest request = CreateLanguageRequest.builder()
      .tag(LANGUAGE_ONE_TAG)
      .correlationId(CORRELATION_ID)
      .build();

  private final LanguageDao dao = mock(LanguageDao.class);
  private final CreateLanguageInDatabaseUseCase uut = new CreateLanguageInDatabaseUseCase(dao);

  @Test
  @DisplayName("Should call the DAO with the expected parameter and return the expected response "
      + "when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    when(dao.save(any())).thenReturn(LANGUAGE_ONE_RESPONSE);

    // WHEN
    LanguageResponse actual = uut.execute(request);

    // THEN
    assertThat(actual.getTag(), is(LANGUAGE_ONE_TAG));
    assertThat(actual.getCorrelationId(), is(CORRELATION_ID));

    verify(dao).save(request);
  }

  @Test
  void shouldThrowEntityAlreadyExistsExceptionWithExpectedMessageWhenLanguageWithTagAlreadyExists() {
    // GIVEN
    when(dao.findByTag(anyString())).thenReturn(Optional.of(LANGUAGE_ONE_RESPONSE));

    String expectedMessage = String.format(
        EntityAlreadyExistsException.MESSAGE_FORMAT,
        "language",
        "tag",
        LANGUAGE_ONE_TAG);

    // WHEN
    EntityAlreadyExistsException exception = assertThrows(
        EntityAlreadyExistsException.class,
        () -> uut.execute(request));

    // THEN
    assertThat(exception.getMessage(), is(expectedMessage));
  }
}