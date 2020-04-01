/* Definition from Keycloak-angular */
export interface KeycloakProfile {
  id?: string;
  username?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  enabled?: boolean;
  emailVerified?: boolean;
  totp?: boolean;
  createdTimestamp?: number;
}


export interface User {
  username: string;
  profile: KeycloakProfile;
}
