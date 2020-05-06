import { UserEffects } from "./user.effects";
import { Subject, of, throwError } from 'rxjs';
import { Action } from '@ngrx/store';
import { mock, instance, when } from 'ts-mockito';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { ofType } from '@ngrx/effects';
import * as UserActions from './user.action';
import { tap } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from 'src/app/model/user/user.keycloak.model';

describe('UserEffects', () => {
    const routerMock = mock(Router);
    const userServiceMock = mock(UserService);
    let actions$ = new Subject<Action>();
    when(userServiceMock.register('', '', '', undefined)).thenReturn(throwError(new HttpErrorResponse({ error: 400 })));
    when(userServiceMock.register('', '', '', 'de-DE')).thenReturn(of({name:'a'} as User));
    let effects: UserEffects;
    beforeEach(() => {
        let router = instance(routerMock);
        let userService = instance(userServiceMock);

        effects = new UserEffects(actions$, router, userService);
    });

    describe('registerUser$',()=>{
        it('should call userService on register and fail with wrong parameters', done => {
            effects.registerUser$
                .pipe(ofType(UserActions.userRegisterFail))
                .subscribe(() => done());
            actions$.next(UserActions.userRegister({ email: '', name: '', password: '', preferredLanguageTag: undefined }));
        });
    
        it('should call userService on register and succeed with correct parameters', done => {
            effects.registerUser$
                .pipe(ofType(UserActions.userRegisterSuccess))
                .subscribe(() => done());
            actions$.next(UserActions.userRegister({ email: '', name: '', password: '', preferredLanguageTag: 'de-DE' }));
        });
    });
    
})