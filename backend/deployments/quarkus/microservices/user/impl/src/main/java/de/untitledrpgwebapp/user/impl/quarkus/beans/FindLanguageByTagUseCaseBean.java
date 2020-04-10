package de.untitledrpgwebapp.user.impl.quarkus.beans;

import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.boundary.mapper.LanguageMapper;
import de.untitledrpgwebapp.language.impl.quarkusrestclient.domain.FindLanguageByTagViaRestUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class FindLanguageByTagUseCaseBean {

  @Produces
  public FindLanguageByTagUseCase findLanguage(
      @RestClient LanguageRestClient client,
      LanguageMapper mapper) {
    return new FindLanguageByTagViaRestUseCase(client, mapper);
  }
}
