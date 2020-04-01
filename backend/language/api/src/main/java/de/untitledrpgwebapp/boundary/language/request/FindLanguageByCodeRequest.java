package de.untitledrpgwebapp.boundary.language.request;

import de.untitledrpgwebapp.boundary.Transfer;

public interface FindLanguageByCodeRequest extends Transfer<FindLanguageByCodeRequest> {

  String getCode();
}
