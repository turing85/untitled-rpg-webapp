package de.untitledrpgwebapp.boundary.user.request;

import de.untitledrpgwebapp.boundary.Transfer;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;

public interface FindUserByNameRequest extends Transfer<FindUserByNameRequest> {

  String getName();

  UserResponseBuilder getUserResponseBuilder();
}
