package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
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
  private final FindLanguageByTagUseCase findLanguageByTag;
  private final CreateAccountUseCase createAccount;

  @Override
  public UserResponse execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    String preferredLanguageTag = request.getPreferredLanguageTag();
    if (findLanguageByTag(preferredLanguageTag, correlationId).isEmpty()) {
      throw new LanguageNotFoundException(preferredLanguageTag, correlationId);
    }
    CreateAccountRequest createAccountRequest = userMapper.requestToRequest(request);
    createAccount.execute(createAccountRequest);
    return userRepository.save(request).toBuilder()
        .correlationId(request.getCorrelationId())
        .build();
  }

  private Optional<String> findLanguageByTag(String languageTag, UUID correlationId) {
    return findLanguageByTag
        .execute(FindLanguageByTagRequest.builder()
            .tag(languageTag)
            .correlationId(correlationId)
            .build()
        ).map(LanguageResponse::getTag);
  }

}
