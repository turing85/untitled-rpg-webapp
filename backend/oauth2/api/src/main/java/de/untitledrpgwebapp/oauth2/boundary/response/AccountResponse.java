package de.untitledrpgwebapp.oauth2.boundary.response;

import de.untitledrpgwebapp.boundary.Correlated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AccountResponse extends Correlated<AccountResponse> {
  final String name;
  final String email;

  protected AccountResponse self() {
    return this;
  }

}
