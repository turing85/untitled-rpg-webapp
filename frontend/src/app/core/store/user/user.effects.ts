import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';
import { UserService } from '../../services/user.service';
import * as UserActions from './user.actions';

@Injectable()
export class UserEffects {

    constructor(
        private actions$: Actions,
        private router: Router,
        private userService: UserService
    ) { }

    registerUser$ = createEffect(() => this.actions$.pipe(
        ofType(UserActions.userRegister),
        mergeMap(
            ({ name, email, password, preferredLanguageTag }) =>
                this.userService.register(name, email, password, preferredLanguageTag).pipe(
                    tap(() => this.router.navigate(['/'])),
                    map(user => UserActions.userRegisterSuccess({ user })),
                    catchError((error) => of(UserActions.userRegisterFail(error)))
                )
        )
    ));


    loadMe$ = createEffect(() => this.actions$.pipe(
        ofType(UserActions.userLoadMe),
        mergeMap(
            () => this.userService.getMe().pipe(
                map(user => UserActions.userLoadMeSuccess({ user })),
                catchError((error) => of(UserActions.userLoadMeFail(error)))
            )
        )
    ));

    loadUser$ = createEffect(() => this.actions$.pipe(
        ofType(UserActions.userLoad),
        mergeMap(
            ({ name }) => this.userService.getUser(name).pipe(
                map(user => UserActions.userLoadSuccess({ user })),
                catchError((error) => of(UserActions.userLoadFail(error)))
            )
        )
    ));

    loadUsers$ = createEffect(() => this.actions$.pipe(
        ofType(UserActions.userLoadAll),
        mergeMap(
            () => this.userService.getUsers().pipe(
                map(users => UserActions.userLoadAllSuccess({ users })),
                catchError((error) => of(UserActions.userLoadAllFail(error)))
            )
        )
    ));

    logout$ = createEffect(() => this.actions$.pipe(
        ofType(UserActions.userLogout),
        tap(() => this.userService.logout())
    ),
        { dispatch: false }
    );
}
