package de.untitledrpgwebapp.language.domain;

import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.CORRELATION_ID;
import static de.untitledrpgwebapp.language.testfixture.LanguageFixture.LANGUAGE_RESPONSES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.boundary.PageAndSortConfig;
import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.boundary.request.FindAllLanguagesRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FindAllLanguagesInDatabaseUseCase unit.")
class FindAllLanguagesInDatabaseUseCaseTest {

  @Test
  @DisplayName("Should call the DAO with the expected parameter and return the expected response "
      + "when everything is ok.")
  void shouldCallRepositoryWithExpectedParametersWhenEverythingIsOk() {
    // GIVEN
    PageAndSortConfig config = mock(PageAndSortConfig.class);
    FindAllLanguagesRequest request =
        FindAllLanguagesRequest.builder().correlationId(CORRELATION_ID).config(config).build();

    LanguageDao dao = mock(LanguageDao.class);
    when(dao.findAll(any())).thenReturn(LANGUAGE_RESPONSES);

    // WHEN
    Collection<LanguageResponse> actual =
        new FindAllLanguagesInDatabaseUseCase(dao).execute(request);

    // THEN
    assertThat(actual, hasSize(LANGUAGE_RESPONSES.size()));
    for (LanguageResponse entry : actual) {
      assertThat(entry.getCorrelationId(), is(CORRELATION_ID));
    }

    verify(dao).findAll(config);
  }
}