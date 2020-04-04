package de.untitledrpgwebapp.user.boundary.response;

import java.io.InputStream;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class CommonUser {

  final String name;
  final String email;
  final String firstName;
  final String lastName;
  final String preferredLanguageCode;
  final LocalDate birthDate;
  final String bio;
  final InputStream avatar;

}