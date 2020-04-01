package de.untitledrpgwebapp.domain.user.impl.config;

import de.untitledrpgwebapp.boundary.auth.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.domain.auth.CreateAccountUseCase;
import de.untitledrpgwebapp.domain.language.FindLanguageByCodeUseCase;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateUserUseCaseImplConfig {

  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final FindLanguageByCodeUseCase fetchLanguageByNameUseCase;
  private CreateAccountUseCase createAccount;
  private Supplier<CreateAccountRequest> createAccountRequestSupplier;
}
