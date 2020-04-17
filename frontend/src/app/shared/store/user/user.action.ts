import { createAction, props } from '@ngrx/store';
import { User } from 'src/app/model/user/user.keycloak.model';
import { LanguageTag } from './../../services/user.service';
import { HttpErrorResponse } from '@angular/common/http';

export const userRegister = createAction('[User] Register', props<{ name: string, email: string, password: string, preferredLanguageTag: LanguageTag }>());
export const userRegisterSuccess = createAction('[User] Register Success', props<{ user: User }>());
export const userRegisterFail = createAction('[User] Register Fail',props<{error: HttpErrorResponse}>());