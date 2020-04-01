package de.untitledrpgwebapp.boundary.user.request;

import de.untitledrpgwebapp.boundary.Transfer;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;

public interface FindAllUsersRequest extends Transfer<FindAllUsersRequest> {

  UserResponseBuilder createUserResponseBuilder();
}
