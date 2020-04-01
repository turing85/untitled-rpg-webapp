import { KeycloakProfile } from 'keycloak-js';
import { User } from './user.keycloak.model';

export function fromKeycloakProfile(keycloakProfile: KeycloakProfile): User {
  return {
    username: keycloakProfile.username,
    profile: keycloakProfile
  } as User;
}
