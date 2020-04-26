import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { EMPTY, of } from 'rxjs';
import { map, mergeMap, catchError, tap } from 'rxjs/operators';
import * as UserActions from './user.action';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

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
                catchError((error) => of(UserActions.userLoadFail(error)))
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

}
