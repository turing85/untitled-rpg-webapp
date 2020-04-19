package de.untitledrpgwebapp.oidc.domain;

import de.untitledrpgwebapp.common.domain.UseCase;
import de.untitledrpgwebapp.oidc.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oidc.boundary.response.AccountResponse;

public interface CreateAccountUseCase
    extends UseCase<CreateAccountRequest, AccountResponse> {
}
