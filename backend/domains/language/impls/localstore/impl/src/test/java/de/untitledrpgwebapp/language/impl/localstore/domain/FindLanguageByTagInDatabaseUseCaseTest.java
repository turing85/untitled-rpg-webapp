package de.untitledrpgwebapp.language.impl.localstore.domain;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_RESPONSE;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_ONE_TAG;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.impl.localstore.boundary.LanguageDao;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindLanguageByTagInDatabaseUseCase unit.")
class FindLanguageByTagInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the DAO with expected parameters and return the expected response when "
      + "everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    FindLanguageByTagRequest request = FindLanguageByTagRequest.builder()
        .tag(LANGUAGE_ONE_TAG)
        .correlationId(CORRELATION_ID)
        .build();

    LanguageDao dao = mock(LanguageDao.class);
    when(dao.findByTag(any())).thenReturn(Optional.of(LANGUAGE_ONE_RESPONSE));

    // WHEN
    Optional<LanguageResponse> fetched =
        new FindLanguageByTagInDatabaseUseCase(dao).execute(request);

    // THEN
    assertTrue(fetched.isPresent());
    assertThat(fetched.get().getCorrelationId(), is(CORRELATION_ID));

    verify(dao).findByTag(LANGUAGE_ONE_TAG);
  }
}