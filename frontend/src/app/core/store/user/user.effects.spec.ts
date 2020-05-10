import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { ofType } from '@ngrx/effects';
import { Action } from '@ngrx/store';
import { of, Subject, throwError } from 'rxjs';
import { User } from 'src/app/model/user/user.keycloak.model';
import { instance, mock, verify, when } from 'ts-mockito';
import { UserService } from '../../services/user.service';
import * as UserActions from './user.actions';
import { UserEffects } from './user.effects';

describe('UserEffects', () => {
    const routerMock = mock(Router);
    const userServiceMock = mock(UserService);
    const actions$ = new Subject<Action>();


    let effects: UserEffects;
    beforeEach(() => {
        effects = new UserEffects(actions$, instance(routerMock), instance(userServiceMock));
    });

    describe('registerUser$', () => {
        it('should call userService on register and fail with wrong parameters', done => {
            when(userServiceMock.register('', '', '', 'de-DE')).thenReturn(throwError(new HttpErrorResponse({ error: 400 })));
            effects.registerUser$
                .pipe(ofType(UserActions.userRegisterFail))
                .subscribe(({ error }) => {
                    expect(error).toEqual(400);
                    done();
                });
            actions$.next(UserActions.userRegister({ email: '', name: '', password: '', preferredLanguageTag: 'de-DE' }));
        });

        it('should call userService on register and succeed with correct parameters', done => {
            when(userServiceMock.register('', '', '', 'de-DE')).thenReturn(of({ name: 'a' } as User));
            effects.registerUser$
                .pipe(ofType(UserActions.userRegisterSuccess))
                .subscribe(action => {
                    expect(action.user.name).toEqual('a');
                    done();
                });
            actions$.next(UserActions.userRegister({ email: '', name: '', password: '', preferredLanguageTag: 'de-DE' }));
        });
    });

    describe('loadMe$', () => {
        it('should call userService on userloadMe', done => {
            when(userServiceMock.getMe()).thenReturn(of({ name: 'a' } as User));
            effects.loadMe$
                .pipe(ofType(UserActions.userLoadMeSuccess))
                .subscribe(action => {
                    expect(action.user.name).toEqual('a');
                    done();
                });
            actions$.next(UserActions.userLoadMe());
        });

        it('should call userService on userloadMe and can fail', done => {
            when(userServiceMock.getMe()).thenReturn(throwError(new HttpErrorResponse({ error: 400 })));
            effects.loadMe$
                .pipe(ofType(UserActions.userLoadMeFail))
                .subscribe(({ error }) => {
                    expect(error).toEqual(400);
                    done();
                });
            actions$.next(UserActions.userLoadMe());
        });
    });

    describe('loadUser$', () => {
        it('should call userService on userLoad', done => {
            when(userServiceMock.getUser('a')).thenReturn(of({ name: 'a' } as User));
            effects.loadUser$
                .pipe(ofType(UserActions.userLoadSuccess))
                .subscribe(action => {
                    expect(action.user.name).toEqual('a');
                    done();
                });
            actions$.next(UserActions.userLoad({ name: 'a' }));
        });

        it('should call userService on userLoad and can fail', done => {
            when(userServiceMock.getUser('a')).thenReturn(throwError(new HttpErrorResponse({ error: 400 })));
            effects.loadUser$
                .pipe(ofType(UserActions.userLoadFail))
                .subscribe(({ error }) => {
                    expect(error).toEqual(400);
                    done();
                });
            actions$.next(UserActions.userLoad({ name: 'a' }));
        });
    });

    describe('loadUsers$', () => {
        it('should call userService on userLoadAll', done => {
            when(userServiceMock.getUsers()).thenReturn(of([{ name: 'a' } as User, { name: 'b' } as User]));
            effects.loadUsers$
                .pipe(ofType(UserActions.userLoadAllSuccess))
                .subscribe(action => {
                    expect(action.users.length).toEqual(2);
                    done();
                });
            actions$.next(UserActions.userLoadAll());
        });

        it('should call userService on userLoadAll and can fail', done => {
            when(userServiceMock.getUsers()).thenReturn(throwError(new HttpErrorResponse({ error: 400 })));
            effects.loadUsers$
                .pipe(ofType(UserActions.userLoadAllFail))
                .subscribe(({ error }) => {
                    expect(error).toEqual(400);
                    done();
                });
            actions$.next(UserActions.userLoadAll());
        });

    });

    describe('logout$', () => {
        it('should call userService on userLoadAll', done => {
            effects.logout$
                .subscribe(() => {
                    verify(userServiceMock.logout()).once();
                    done();
                });
            actions$.next(UserActions.userLogout());

        });


    });
});
