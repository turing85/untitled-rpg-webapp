package de.untitledrpgwebapp.boundary.language.response;

import de.untitledrpgwebapp.boundary.Transfer;

public interface LanguageResponseBuilder extends Transfer<LanguageResponseBuilder> {

  String getCode();

  LanguageResponseBuilder setCode(String code);

  String getName();

  LanguageResponseBuilder setName(String name);
}
