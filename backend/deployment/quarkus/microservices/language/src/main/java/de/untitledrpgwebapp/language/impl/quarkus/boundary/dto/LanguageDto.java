package de.untitledrpgwebapp.language.impl.quarkus.boundary.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonPropertyOrder({ "tag", "name" })
public class LanguageDto {

  private final String tag;
  private final String name;
}
