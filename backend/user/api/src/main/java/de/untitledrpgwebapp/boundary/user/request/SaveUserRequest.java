package de.untitledrpgwebapp.boundary.user.request;

import de.untitledrpgwebapp.boundary.Transfer;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;

public interface SaveUserRequest extends Transfer<SaveUserRequest> {

  SaveUserRequest setName(String name);

  SaveUserRequest setEmail(String name);

  SaveUserRequest setDisplayName(String displayName);

  SaveUserRequest setCreated(Instant created);

  SaveUserRequest setFirstName(String firstName);

  SaveUserRequest setLastName(String lastName);

  String getPreferredLanguage();

  SaveUserRequest setPreferredLanguage(String preferredLanguage);

  SaveUserRequest setBirthDate(LocalDate birthDate);

  SaveUserRequest setBio(String bio);

  SaveUserRequest setAvatar(InputStream avatar);

}
