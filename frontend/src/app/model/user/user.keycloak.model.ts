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
  email: string;
  name: string;
  preferences: any;
  preferredLanguageTag: string;
}
