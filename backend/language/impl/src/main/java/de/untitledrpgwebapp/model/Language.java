package de.untitledrpgwebapp.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class Language {
  @NonNull
  private final String code;

  @NonNull
  private final String name;
}
