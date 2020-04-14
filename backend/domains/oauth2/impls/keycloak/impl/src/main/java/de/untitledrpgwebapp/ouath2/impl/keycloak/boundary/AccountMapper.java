package de.untitledrpgwebapp.ouath2.impl.keycloak.boundary;

import de.untitledrpgwebapp.oauth2.boundary.request.CreateAccountRequest;
import de.untitledrpgwebapp.oauth2.boundary.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

  AccountResponse requestToResponse(CreateAccountRequest request);
}
