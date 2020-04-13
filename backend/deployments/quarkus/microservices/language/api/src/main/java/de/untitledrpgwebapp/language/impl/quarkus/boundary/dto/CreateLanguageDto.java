package de.untitledrpgwebapp.language.impl.quarkus.boundary.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RegisterForReflection
@Getter
@Setter
@NoArgsConstructor
public class CreateLanguageDto {

  @NotNull
  @Pattern(regexp = "[a-z]{2}(?:-[A-Z]{2})?")
  private String tag;

  @NotNull
  @Size(max = 255)
  private String name;
}
