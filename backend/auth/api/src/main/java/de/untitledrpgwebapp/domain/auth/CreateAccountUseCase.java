package de.untitledrpgwebapp.domain.auth;

import de.untitledrpgwebapp.boundary.auth.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.auth.response.AccountResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;

public interface CreateAccountUseCase
    extends UseCase<CreateAccountRequest, AccountResponseBuilder> {
}
