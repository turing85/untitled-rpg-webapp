package de.untitledrpgwebapp.boundary.user.request;

import de.untitledrpgwebapp.boundary.Transfer;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import java.io.InputStream;
import java.time.LocalDate;

public interface CreateUserRequest extends Transfer<CreateUserRequest> {

  String getName();

  String getEmail();

  String getDisplayName();

  String getFirstName();

  String getLastName();

  String getPreferredLanguageCode();

  LocalDate getBirthDate();

  String getBio();

  InputStream getAvatar();

  UserResponseBuilder getUserResponseBuilder();
}
