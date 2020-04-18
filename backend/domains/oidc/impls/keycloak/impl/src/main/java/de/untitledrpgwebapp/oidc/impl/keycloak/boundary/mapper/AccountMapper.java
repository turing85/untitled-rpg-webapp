package de.untitledrpgwebapp.oidc.impl.keycloak.boundary.mapper;

import de.untitledrpgwebapp.oidc.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oidc.boundary.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

  AccountResponse requestToResponse(CreateAccountRequest request);
}
