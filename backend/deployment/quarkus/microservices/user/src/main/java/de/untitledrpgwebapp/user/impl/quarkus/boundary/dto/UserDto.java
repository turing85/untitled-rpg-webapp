package de.untitledrpgwebapp.user.impl.quarkus.boundary.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

  private String name;
  private String email;
  private String firstName;
  private String lastName;
  private String preferredLanguageTag;
  private LocalDate birthDate;
  private String bio;
  private byte[] avatar;
}
