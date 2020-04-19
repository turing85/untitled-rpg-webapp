package de.untitledrpgwebapp.user.boundary.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RegisterForReflection
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CreateUserDto extends CommonUserDto {

  @NotNull
  private String password;
}
