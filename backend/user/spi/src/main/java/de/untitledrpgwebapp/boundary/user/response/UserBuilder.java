package de.untitledrpgwebapp.boundary.user.response;

import de.untitledrpgwebapp.boundary.Transfer;

public interface UserBuilder extends CommonUserBuilder, Transfer<UserBuilder> {

  long getId();
}
