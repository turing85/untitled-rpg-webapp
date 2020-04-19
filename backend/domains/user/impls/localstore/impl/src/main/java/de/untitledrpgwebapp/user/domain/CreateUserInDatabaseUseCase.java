package de.untitledrpgwebapp.user.domain;

import de.untitledrpgwebapp.common.domain.exception.DependencyNotFoundException;
import de.untitledrpgwebapp.common.domain.exception.EntityAlreadyExistsException;
import de.untitledrpgwebapp.language.boundary.request.FindLanguageByTagRequest;
import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;
import de.untitledrpgwebapp.language.domain.FindLanguageByTagUseCase;
import de.untitledrpgwebapp.oidc.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oidc.domain.CreateAccountUseCase;
import de.untitledrpgwebapp.user.boundary.UserDao;
import de.untitledrpgwebapp.user.boundary.mapper.UserMapper;
import de.untitledrpgwebapp.user.boundary.request.CreateUserRequest;
import de.untitledrpgwebapp.user.boundary.response.UserResponse;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserInDatabaseUseCase implements CreateUserUseCase {

  private final UserMapper mapper;
  private final UserDao dao;
  private final FindLanguageByTagUseCase findLanguage;
  private final CreateAccountUseCase createAccount;

  @Transactional
  @Override
  public UserResponse execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();

    verifyNameAndEmailAreAvailable(request.getName(), request.getEmail(), correlationId);

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

  private void verifyNameAndEmailAreAvailable(String name, String email, UUID correlationId) {
    if (dao.findByName(name).isPresent()) {
      throw EntityAlreadyExistsException.userWithName(name, correlationId);
    }
    if (dao.findByEmail(email).isPresent()) {
      throw EntityAlreadyExistsException.userWithEmail(email, correlationId);
    }
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
