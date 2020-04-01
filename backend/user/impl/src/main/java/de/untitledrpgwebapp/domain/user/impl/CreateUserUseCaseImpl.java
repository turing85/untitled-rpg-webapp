package de.untitledrpgwebapp.domain.user.impl;

import de.untitledrpgwebapp.boundary.user.UserRepository;
import de.untitledrpgwebapp.boundary.user.request.CreateUserRequest;
import de.untitledrpgwebapp.boundary.user.request.SaveUserRequest;
import de.untitledrpgwebapp.boundary.user.request.impl.FetchLanguageByCodeRequestImpl;
import de.untitledrpgwebapp.boundary.user.request.mapper.CreateUserRequestMapper;
import de.untitledrpgwebapp.boundary.user.request.mapper.SaveUserRequestMapper;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import de.untitledrpgwebapp.boundary.language.response.mapper.LanguageResponseBuilderMapper;
import de.untitledrpgwebapp.domain.language.FetchLanguageByCodeUseCase;
import de.untitledrpgwebapp.domain.user.CreateUserUseCase;
import de.untitledrpgwebapp.domain.user.impl.config.CreateUserUseCaseImplConfig;
import de.untitledrpgwebapp.model.Language;
import de.untitledrpgwebapp.model.User;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

  private final CreateUserRequestMapper createUserRequestMapper;
  private final SaveUserRequestMapper saveUserRequestMapper;
  private final LanguageResponseBuilderMapper languageResponseBuilderMapper;

  private final UserRepository userRepository;
  private final FetchLanguageByCodeUseCase fetchLanguageByNameUseCase;
  private Supplier<SaveUserRequest> saveUserRequestSupplier;

  /**
   * A Config-constructor.
   *
   * @param config
   *     the {@link CreateUserUseCaseImplConfig}, configuring this instance.
   */
  public CreateUserUseCaseImpl(CreateUserUseCaseImplConfig config) {
    this(
        config.getCreateUserRequestMapper(),
        config.getSaveUserRequestMapper(),
        config.getLanguageResponseBuilderMapper(),
        config.getUserRepository(),
        config.getSaveUserRequestSupplier(),
        config.getFetchLanguageByNameUseCase());
  }

  CreateUserUseCaseImpl(
      CreateUserRequestMapper createUserRequestMapper,
      SaveUserRequestMapper saveUserRequestMapper,
      LanguageResponseBuilderMapper languageResponseBuilderMapper,
      UserRepository userRepository,
      Supplier<SaveUserRequest> saveUserRequestSupplier,
      FetchLanguageByCodeUseCase fetchLanguabeByNameUseCase) {
    this.createUserRequestMapper = createUserRequestMapper;
    this.saveUserRequestMapper = saveUserRequestMapper;
    this.languageResponseBuilderMapper = languageResponseBuilderMapper;
    this.userRepository = userRepository;
    this.saveUserRequestSupplier = saveUserRequestSupplier;
    this.fetchLanguageByNameUseCase = fetchLanguabeByNameUseCase;
  }

  @Override
  public UserResponseBuilder execute(CreateUserRequest request) {
    UUID correlationId = request.getCorrelationId();
    String languageCode = request.getPreferredLanguageCode();
    Language language;
    if (Objects.isNull(languageCode)) {
      language = null;
    } else {
      language = fetchLanguageByCode(correlationId, languageCode);
    }
    User toSave = createUserRequestMapper.requestToUser(request).setPreferredLanguage(language);
    return saveUser(correlationId, toSave);
  }

  private Language fetchLanguageByCode(UUID correlationId, String preferredLanguageCode) {
    FetchLanguageByCodeRequestImpl fetchLanguageRequest = FetchLanguageByCodeRequestImpl.builder()
        .correlationId(correlationId)
        .code(preferredLanguageCode)
        .build();
    return fetchLanguageByNameUseCase
        .execute(fetchLanguageRequest)
        .map(languageResponseBuilderMapper::responseToEntity)
        .orElseThrow();
  }

  private UserResponseBuilder saveUser(UUID correlationId, User toSave) {
    SaveUserRequest saveRequest = saveUserRequestMapper
        .entityToRequest(toSave, saveUserRequestSupplier.get())
        .setCorrelationId(correlationId);
    return userRepository.save(saveRequest)
        .map(saved -> saved.setCorrelationId(correlationId))
        .orElseThrow();
  }

}
