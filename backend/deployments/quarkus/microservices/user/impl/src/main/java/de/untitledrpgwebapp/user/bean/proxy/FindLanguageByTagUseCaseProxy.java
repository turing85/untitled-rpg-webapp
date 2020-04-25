package de.untitledrpgwebapp.user.bean.proxy;

import de.untitledrpgwebapp.language.boundary.LanguageRestClient;
import de.untitledrpgwebapp.language.boundary.mapper.LanguageRestMapper;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagViaRestUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Traced
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FindLanguageByTagUseCaseProxy implements FindLanguageByTagUseCase {

  @Delegate
  private final FindLanguageByTagViaRestUseCase findLanguage;

  @Inject
  public FindLanguageByTagUseCaseProxy(
      @RestClient LanguageRestClient client,
      LanguageRestMapper mapper) {
    this(new FindLanguageByTagViaRestUseCase(client, mapper));
  }
}
