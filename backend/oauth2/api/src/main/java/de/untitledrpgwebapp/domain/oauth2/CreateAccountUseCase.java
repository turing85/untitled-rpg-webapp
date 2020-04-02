package de.untitledrpgwebapp.domain.oauth2;

import de.untitledrpgwebapp.boundary.oauth2.request.CreateAccountRequest;
import de.untitledrpgwebapp.boundary.oauth2.response.AccountResponseBuilder;
import de.untitledrpgwebapp.domain.UseCase;

public interface CreateAccountUseCase
    extends UseCase<CreateAccountRequest, AccountResponseBuilder> {

}
