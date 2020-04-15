package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.domain.exception.DependencyNotFoundException;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
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
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserInDatabaseUseCase implements CreateUserUseCase {

  private final UserMapper mapper;
  private final UserRepository repository;
  private final FindLanguageByTagUseCase findLanguage;
  private final CreateAccountUseCase createAccount;

  @Override
  public UserResponse execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    String preferredLanguageTag = request.getPreferredLanguageTag();
    if (findLanguageByTag(preferredLanguageTag, correlationId).isEmpty()) {
      throw DependencyNotFoundException.languageWithTag(preferredLanguageTag, correlationId);
    }
    CreateAccountRequest createAccountRequest = mapper.requestToRequest(request);
    createAccount.execute(createAccountRequest);
    return repository.save(request).toBuilder()
        .correlationId(request.getCorrelationId())
        .build();
  }

  private Optional<String> findLanguageByTag(String languageTag, UUID correlationId) {
    return findLanguage
        .execute(FindLanguageByTagRequest.builder()
            .tag(languageTag)
            .correlationId(correlationId)
            .build()
        ).map(LanguageResponse::getTag);
  }

}
