package de.untitledrpgwebapp.boundary.language.request;

import de.untitledrpgwebapp.boundary.Transfer;

public interface FetchLanguageByCodeRequest extends Transfer<FetchLanguageByCodeRequest> {

  String getCode();
}
