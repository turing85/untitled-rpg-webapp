package de.untitledrpgwebapp.oauth2.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class CreateAccountRequest extends Correlated<CreateAccountRequest> {

  final String name;
  final String email;
  final String password;
}
