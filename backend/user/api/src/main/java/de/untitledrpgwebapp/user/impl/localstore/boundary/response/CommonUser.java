package de.untitledrpgwebapp.user.impl.localstore.boundary.response;

import de.untitledrpgwebapp.boundary.Correlated;
import java.io.InputStream;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class CommonUser<T extends CommonUser<T>> extends Correlated<T> {

  final String name;
  final String email;
  final String firstName;
  final String lastName;
  final String preferredLanguageCode;
  final LocalDate birthDate;
  final String bio;
  final InputStream avatar;

}