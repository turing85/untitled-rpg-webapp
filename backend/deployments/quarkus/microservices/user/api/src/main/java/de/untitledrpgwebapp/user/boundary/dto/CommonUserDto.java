package de.untitledrpgwebapp.user.boundary.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDate;
import java.util.Map;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RegisterForReflection
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class CommonUserDto {
  @NotNull
  @Pattern(regexp = "[a-zA-Z0-9\\-]{3,255}")
  private String name;

  @NotNull
  @Email
  private String email;

  @Size(max = 255)
  private String firstName;

  @Size(max = 255)
  private String lastName;

  @NotNull
  @Pattern(regexp = "[a-z]{2}(?:-[A-Z]{2})?")
  private String preferredLanguageTag;

  private LocalDate birthDate;
  private String bio;
  private byte[] avatar;
  private Map<String, Object> preferences;
}
