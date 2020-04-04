package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByCodeRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByCodeUseCase;
import de.untitledrpgwebapp.language.exception.LanguageNotFoundException;
import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserRepository;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserInDatabaseUseCase implements CreateUserUseCase {

  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final FindLanguageByCodeUseCase fetchLanguageByCode;
  private final CreateAccountUseCase createAccount;

  @Override
  public UserResponse execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    String preferredLanguageCode = request.getPreferredLanguageCode();
    if (fetchLanguageCodeByCode(correlationId, preferredLanguageCode).isEmpty()) {
      throw new LanguageNotFoundException(preferredLanguageCode, correlationId);
    }
    CreateAccountRequest createAccountRequest = userMapper.requestToRequest(request);
    createAccount.execute(createAccountRequest);
    return userMapper.entityToResponse(userRepository.save(request))
        .toBuilder().correlationId(request.getCorrelationId()).build();
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
