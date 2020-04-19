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
        private router:Router,
        private userService: UserService
    ) { }

    registerUser$ = createEffect(() => this.actions$.pipe(
        ofType(UserActions.userRegister),
        mergeMap(
            ({ name, email, password, preferredLanguageTag }) => this.userService.register(name, email, password, preferredLanguageTag).pipe(
                tap(user=>this.router.navigate(["/"])),
                map(user => UserActions.userRegisterSuccess({ user })),
                catchError((error) => of(UserActions.userRegisterFail(error)))
            )
        )
    ));


}