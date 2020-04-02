package de.untitledrpgwebapp.boundary.oauth2.request;

import de.untitledrpgwebapp.boundary.Transfer;
import de.untitledrpgwebapp.boundary.oauth2.response.AccountResponseBuilder;

public interface CreateAccountRequest extends Transfer<CreateAccountRequest> {

  String getName();

  CreateAccountRequest setName(String name);

  String getEmail();

  CreateAccountRequest setEmail(String email);

  String getPassword();

  CreateAccountRequest setPassword(String password);

  AccountResponseBuilder getAccountResponseBuilder();
}
