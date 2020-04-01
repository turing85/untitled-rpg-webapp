package de.untitledrpgwebapp.boundary.auth.request;

import de.untitledrpgwebapp.boundary.Transfer;

public interface CreateAccountRequest extends Transfer<CreateAccountRequest> {

  CreateAccountRequest setName(String name);

  CreateAccountRequest setEmail(String email);

  CreateAccountRequest setPassword(String password);
}
