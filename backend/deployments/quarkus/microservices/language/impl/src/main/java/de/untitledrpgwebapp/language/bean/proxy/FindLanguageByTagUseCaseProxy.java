package de.untitledrpgwebapp.language.bean.proxy;

import de.untitledrpgwebapp.language.boundary.LanguageDao;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagInDatabaseUseCase;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FindLanguageByTagUseCaseProxy implements FindLanguageByTagUseCase {

  @Delegate
  private final FindLanguageByTagInDatabaseUseCase findByTag;

  @Inject
  public FindLanguageByTagUseCaseProxy(LanguageDao dao) {
    this(new FindLanguageByTagInDatabaseUseCase(dao));
  }
}
