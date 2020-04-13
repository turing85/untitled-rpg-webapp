package de.untitledrpgwebapp.user.impl.localstore.testfixture;

import static de.untitledrpgwebapp.user.impl.localstore.testfixture.UserData.TAG_ONE;

import de.untitledrpgwebapp.language.boundary.response.LanguageResponse;

public class LanguageData {

  public static final LanguageResponse RESPONSE =
      LanguageResponse.builder().tag(TAG_ONE).build();
}
