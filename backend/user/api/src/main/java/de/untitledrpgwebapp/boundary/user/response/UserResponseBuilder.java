package de.untitledrpgwebapp.boundary.user.response;

import de.untitledrpgwebapp.boundary.Transfer;
import de.untitledrpgwebapp.boundary.language.response.LanguageResponseBuilder;
import java.io.InputStream;
import java.time.LocalDate;

public interface UserResponseBuilder extends Transfer<UserResponseBuilder> {

  String getName();

  UserResponseBuilder setName(final String name);

  String getEmail();

  UserResponseBuilder setEmail(final String email);

  String getFirstName();

  UserResponseBuilder setFirstName(final String firstName);

  String getLastName();

  UserResponseBuilder setLastName(final String lastName);

  LanguageResponseBuilder getPreferredLanguage();

  UserResponseBuilder setPreferredLanguage(final String preferredLanguage);

  LocalDate getBirthDate();

  UserResponseBuilder setBirthDate(final LocalDate birthDate);

  String getBio();

  UserResponseBuilder setBio(final String bio);

  InputStream getAvatar();

  UserResponseBuilder setAvatar(InputStream avatar);
}
