package de.untitledrpgwebapp.user.impl.quarkus.boundary.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RegisterForReflection
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CreateUserDto {

  @NotNull
  @Pattern(regexp = "[a-zA-Z0-9\\-]{3,255}")
  private String name;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;

  @NotNull
  @Pattern(regexp = "[a-z]{2}(?:-[A-Z]{2})?")
  private String preferredLanguageTag;
}
