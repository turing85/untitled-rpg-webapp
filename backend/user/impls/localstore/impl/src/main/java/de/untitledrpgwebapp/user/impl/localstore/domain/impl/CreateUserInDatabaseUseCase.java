package de.untitledrpgwebapp.user.impl.localstore.domain.impl;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByCodeUseCase;
import de.untitledrpgwebapp.language.exception.LanguageNotFoundException;
import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.UserRepository;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.impl.localstore.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserEntity;
import de.untitledrpgwebapp.user.impl.localstore.boundary.response.UserResponse;
import java.util.Optional;
import java.util.UUID;

public class CreateUserInDatabaseUseCase implements CreateUserUseCase {

  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final FindLanguageByCodeUseCase fetchLanguageByCode;
  private final CreateAccountUseCase createAccount;

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
   */
  public CreateUserInDatabaseUseCase(
      UserMapper userMapper,
      UserRepository userRepository,
      FindLanguageByCodeUseCase findLanguageByNameUseCase,
      CreateAccountUseCase createAccount) {
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.fetchLanguageByCode = findLanguageByNameUseCase;
    this.createAccount = createAccount;
  }

  @Override
  public UserResponse execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    String preferredLanguageCode = request.getPreferredLanguageCode();
    if (fetchLanguageCodeByCode(correlationId, preferredLanguageCode).isEmpty()) {
      throw new LanguageNotFoundException(preferredLanguageCode, correlationId);
    }
    CreateAccountRequest createAccountRequest = userMapper.requestToRequest(request).toBuilder()
        .correlationId(correlationId)
        .build();
    createAccount.execute(createAccountRequest);
    Optional<UserEntity> builder = userRepository.save(request);
    return builder
        .map(userMapper::entityToRequest)
        .map(response -> response.toBuilder().correlationId(correlationId).build())
        .orElseThrow();
  }

  private Optional<String> fetchLanguageCodeByCode(UUID correlationId, String languageCode) {
    return fetchLanguageByCode
        .execute(FindLanguageByCodeRequest.builder()
            .correlationId(correlationId)
            .code(languageCode)
            .build()
        ).map(LanguageResponse::getCode);
  }

}
