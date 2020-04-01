package de.untitledrpgwebapp.boundary.user.response;

import java.io.InputStream;
import java.time.LocalDate;

public interface CommonUserBuilder {

  String getName();

  UserResponseBuilder setName(final String name);

  String getEmail();

  UserResponseBuilder setEmail(final String email);

  String getFirstName();

  UserResponseBuilder setFirstName(final String firstName);

  String getLastName();

  UserResponseBuilder setLastName(final String lastName);

  String getPreferredLanguageCode();

  UserResponseBuilder setPreferredLanguage(final String preferredLanguage);

  LocalDate getBirthDate();

  UserResponseBuilder setBirthDate(final LocalDate birthDate);

  String getBio();

  UserResponseBuilder setBio(final String bio);

  InputStream getAvatar();

  UserResponseBuilder setAvatar(InputStream avatar);
}
