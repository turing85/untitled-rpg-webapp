package de.untitledrpgwebapp.user.beans;

import de.untitledrpgwebapp.language.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.boundary.mapper.LanguageRestMapper;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagViaRestUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class FindLanguageByTagUseCaseBean {

  @Produces
  public FindLanguageByTagUseCase findLanguage(
      @RestClient LanguageRestClient client,
      LanguageRestMapper mapper) {
    return new FindLanguageByTagViaRestUseCase(client, mapper);
  }
}
