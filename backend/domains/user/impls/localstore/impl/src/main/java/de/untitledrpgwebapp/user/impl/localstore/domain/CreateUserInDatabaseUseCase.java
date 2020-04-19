package de.untitledrpgwebapp.user.impl.localstore.domain;

import de.untitledrpgwebapp.domain.exception.DependencyNotFoundException;
import de.untitledrpgwebapp.domain.exception.EntityAlreadyExistsException;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.request.FindUserByEmailRequest;
import de.untitledrpgwebapp.user.boundary.request.FindUserByNameRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import de.untitledrpgwebapp.user.domain.CreateUserUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByEmailUseCase;
import de.untitledrpgwebapp.user.domain.FindUserByNameUseCase;
import de.untitledrpgwebapp.user.impl.localstore.boundary.mapper.UserMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserInDatabaseUseCase implements CreateUserUseCase {

  private final UserMapper mapper;
  private final UserDao dao;
  private final FindUserByNameUseCase findByName;
  private final FindUserByEmailUseCase findByEmail;
  private final FindLanguageByTagUseCase findLanguage;
  private final CreateAccountUseCase createAccount;

  @Override
  public UserResponse execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    String name = request.getName();
    boolean userWithNameIsPresent = findByName.execute(FindUserByNameRequest.builder()
        .name(name)
        .correlationId(correlationId)
        .build()
    ).isPresent();
    if (userWithNameIsPresent) {
      throw EntityAlreadyExistsException.userWithName(name, correlationId);
    }

    String email = request.getEmail();
    boolean userWithEmailIsPresent = findByEmail.execute(FindUserByEmailRequest.builder()
        .email(email)
        .correlationId(correlationId)
        .build()
    ).isPresent();
    if (userWithEmailIsPresent) {
      throw EntityAlreadyExistsException.userWithEmail(email, correlationId);
    }

    String preferredLanguageTag = request.getPreferredLanguageTag();
    if (findLanguageByTag(preferredLanguageTag, correlationId).isEmpty()) {
      throw DependencyNotFoundException.languageWithTag(preferredLanguageTag, correlationId);
    }
    CreateAccountRequest createAccountRequest = mapper.requestToRequest(request);
    createAccount.execute(createAccountRequest);
    return dao.save(request).toBuilder()
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
