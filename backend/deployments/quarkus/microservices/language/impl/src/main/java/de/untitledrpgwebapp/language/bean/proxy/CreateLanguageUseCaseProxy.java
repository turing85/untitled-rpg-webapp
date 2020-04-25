package de.untitledrpgwebapp.language.bean.proxy;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.domain.CreateLanguageInDatabaseUseCase;
import de.untitledrpgwebapp.language.domain.CreateLanguageUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateLanguageUseCaseProxy implements CreateLanguageUseCase {

  @Delegate
  private final CreateLanguageInDatabaseUseCase createLanguage;

  @Inject
  public CreateLanguageUseCaseProxy(LanguageDao dao) {
    this(new CreateLanguageInDatabaseUseCase(dao));
  }
}
