import { createAction, props } from '@ngrx/store';
import { User } from 'src/app/model/user/user.keycloak.model';
import { LanguageTag } from '../../services/user.service';
import { HttpErrorResponse } from '@angular/common/http';

export const userRegister = createAction('[User] Register',
    props<{ name: string, email: string, password: string, preferredLanguageTag: LanguageTag }>()
);

export const userLogout = createAction('[User] Logout');

export const userRegisterSuccess = createAction('[User] Register Success', props<{ user: User }>());
export const userRegisterFail = createAction('[User] Register Fail', props<{ error: HttpErrorResponse }>());


export const userLoadMe = createAction('[User] Load me');
export const userLoadMeSuccess = createAction('[User] Load me Success', props<{ user: User }>());
export const userLoadMeFail = createAction('[User] Load me Fail', props<{ error: HttpErrorResponse }>());

export const userLoad = createAction('[User] Load user', props<{ name: string }>());
export const userLoadSuccess = createAction('[User] Load user Success', props<{ user: User }>());
export const userLoadFail = createAction('[User] Load user Fail', props<{ error: HttpErrorResponse }>());


export const userLoadAll = createAction('[User] Load all user');
export const userLoadAllSuccess = createAction('[User] Load all user Success', props<{ users: User[] }>());
export const userLoadAllFail = createAction('[User] Load all user Fail', props<{ error: HttpErrorResponse }>());
