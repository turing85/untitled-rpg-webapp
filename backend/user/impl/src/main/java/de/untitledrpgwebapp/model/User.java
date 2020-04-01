package de.untitledrpgwebapp.model;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class User extends BaseEntity<User> {

  @NonNull
  private final String name;

  @NonNull
  private final String email;

  @NonNull
  private final Instant created = Instant.now();

  private String displayName;
  private String firstName;
  private String lastName;
  private Language preferredLanguage;
  private LocalDate birthDate;
  private String bio;
  private InputStream avatar;

  @Override
  protected User self() {
    return this;
  }
}
