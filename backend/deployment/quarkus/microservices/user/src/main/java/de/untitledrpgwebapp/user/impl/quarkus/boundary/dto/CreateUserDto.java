package de.untitledrpgwebapp.user.impl.quarkus.boundary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

  private String name;
  private String email;
}
