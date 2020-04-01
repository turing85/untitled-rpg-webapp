package de.untitledrpgwebapp.domain.user.impl.config;

import de.untitledrpgwebapp.boundary.language.response.mapper.LanguageResponseBuilderMapper;
import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.request.SaveUserRequest;
import de.untitledrpgwebapp.boundary.user.request.mapper.CreateUserRequestMapper;
import de.untitledrpgwebapp.boundary.user.request.mapper.SaveUserRequestMapper;
import de.untitledrpgwebapp.domain.language.FetchLanguageByCodeUseCase;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateUserUseCaseImplConfig {

  private final CreateUserRequestMapper createUserRequestMapper;
  private final SaveUserRequestMapper saveUserRequestMapper;
  private final LanguageResponseBuilderMapper languageResponseBuilderMapper;
  private final UserRepository userRepository;
  private final FetchLanguageByCodeUseCase fetchLanguageByNameUseCase;
  private Supplier<SaveUserRequest> saveUserRequestSupplier;
}
