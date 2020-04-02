package de.untitledrpgwebapp.domain.user.impl;

import de.untitledrpgwebapp.boundary.language.request.impl.FindLanguageByCodeRequestImpl;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import de.untitledrpgwebapp.boundary.oauth2.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.mapper.UserMapper;
import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.domain.language.FindLanguageByCodeUseCase;
import de.untitledrpgwebapp.domain.oauth2.CreateAccountUseCase;
import de.untitledrpgwebapp.domain.user.CreateUserUseCase;
import de.untitledrpgwebapp.exception.LanguageNotFoundException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class CreateUserInDatabaseUseCase implements CreateUserUseCase {

  private final UserMapper userMapper;

  private final UserRepository userRepository;
  private final FindLanguageByCodeUseCase fetchLanguageByName;

  private final CreateAccountUseCase createAccount;
  private Supplier<CreateAccountRequest> createAccountRequestSupplier;


  /**
   * Instantiates a new Create user in database use case.
   *
   * @param userMapper
   *     the user mapper
   * @param userRepository
   *     the user repository
   * @param findLanguageByNameUseCase
   *     the find language by name use case
   * @param createAccount
   *     the create account
   * @param createAccountRequestSupplier
   *     the create account request supplier
   */
  public CreateUserInDatabaseUseCase(
      UserMapper userMapper,
      UserRepository userRepository,
      FindLanguageByCodeUseCase findLanguageByNameUseCase,
      CreateAccountUseCase createAccount,
      Supplier<CreateAccountRequest> createAccountRequestSupplier) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.fetchLanguageByName = findLanguageByNameUseCase;
    this.createAccount = createAccount;
    this.createAccountRequestSupplier = createAccountRequestSupplier;
  }

  @Override
  public UserResponseBuilder execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    final String preferredLanguageCode = request.getPreferredLanguageCode();
    if (fetchLanguageCodeByCode(correlationId, preferredLanguageCode).isEmpty()) {
      throw new LanguageNotFoundException(preferredLanguageCode, correlationId);
    }
    final CreateAccountRequest createAccountRequest =
        userMapper.requestToRequest(request, createAccountRequestSupplier.get())
            .setCorrelationId(correlationId);
    createAccount.execute(
        createAccountRequest);
    Optional<UserBuilder> builder = userRepository.save(request);
    return builder
        .map(saved -> saved.setCorrelationId(correlationId))
        .map(saved -> userMapper.requestToRequest(saved, request.getUserResponseBuilder()))
        .orElseThrow();
  }

  private Optional<String> fetchLanguageCodeByCode(UUID correlationId, String languageCode) {
    return fetchLanguageByName
        .execute(FindLanguageByCodeRequestImpl.builder()
            .correlationId(correlationId)
            .code(languageCode)
            .build()
        ).map(LanguageResponseBuilder::getCode);
  }

}
