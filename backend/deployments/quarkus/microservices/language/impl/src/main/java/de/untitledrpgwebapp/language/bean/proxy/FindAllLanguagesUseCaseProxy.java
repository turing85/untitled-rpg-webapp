package de.untitledrpgwebapp.language.bean.proxy;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesInDatabaseUseCase;
import de.untitledrpgwebapp.language.domain.FindAllLanguagesUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindAllLanguagesUseCaseProxy implements FindAllLanguagesUseCase {

  @Delegate
  private final FindAllLanguagesInDatabaseUseCase findAllLanguages;

  @Inject
  public FindAllLanguagesUseCaseProxy(LanguageDao dao) {
    this(new FindAllLanguagesInDatabaseUseCase(dao));
  }
}
