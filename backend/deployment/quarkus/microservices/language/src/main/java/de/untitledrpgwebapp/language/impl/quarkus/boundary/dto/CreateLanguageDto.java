package de.untitledrpgwebapp.language.impl.quarkus.boundary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLanguageDto {
  private String tag;
  private String name;
}
