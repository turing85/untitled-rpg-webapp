package de.untitledrpgwebapp.user.impl.localstore.boundary.request;

import de.untitledrpgwebapp.boundary.Correlated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindUserByNameRequest extends Correlated<FindUserByNameRequest> {

  final String name;

  protected FindUserByNameRequest self() {
    return this;
  }
}
