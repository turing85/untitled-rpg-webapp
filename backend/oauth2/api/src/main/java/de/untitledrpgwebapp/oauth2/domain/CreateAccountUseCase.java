package de.untitledrpgwebapp.oauth2.domain;

import de.untitledrpgwebapp.domain.UseCase;
import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.boundary.response.AccountResponse;

public interface CreateAccountUseCase
    extends UseCase<CreateAccountRequest, AccountResponse> {
}
