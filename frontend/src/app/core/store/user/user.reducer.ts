import { createReducer, on } from '@ngrx/store';
import { EntityState, createEntityAdapter } from '@ngrx/entity';
import * as UserActions from './user.action';
import { User } from 'src/app/model/user/user.keycloak.model';

export interface UserState extends EntityState<User> {
    me: string;
    loading: boolean;
}


export const userAdapter = createEntityAdapter<User>({ selectId: user => user.name });

const initialState: UserState = userAdapter.getInitialState({
    me: undefined,
    loading: false,
});

export const userReducer = createReducer(initialState,
    // Register
    on(UserActions.userRegister, state => ({ ...state, loading: true })),
    on(UserActions.userRegisterFail, state => ({ ...state, loading: false })),
    on(UserActions.userRegisterSuccess, (state, { user }) => userAdapter.upsertOne(user, { ...state, loading: false, me: user.name })),

    // Load User
    on(UserActions.userLoad, state => ({ ...state, loading: true })),
    on(UserActions.userLoadFail, state => ({ ...state, loading: false })),
    on(UserActions.userLoadSuccess, (state, { user }) => userAdapter.upsertOne(user, { ...state, loading: false })),

    on(UserActions.userLoadMe, state => ({ ...state, loading: true })),
    on(UserActions.userLoadMeSuccess, (state, { user }) => userAdapter.upsertOne(user, { ...state, loading: false, me: user.name })),

    // Load All User
    on(UserActions.userLoadAll, state => ({ ...state, loading: true })),
    on(UserActions.userLoadAllFail, state => ({ ...state, loading: false })),
    on(UserActions.userLoadAllSuccess, (state, { users }) => userAdapter.upsertMany(users, { ...state, loading: false })),
);

