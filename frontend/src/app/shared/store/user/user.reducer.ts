import { createReducer, on, Action } from '@ngrx/store';
import { userRegister, userRegisterFail, userRegisterSuccess } from './user.action';
import { User } from 'src/app/model/user/user.keycloak.model';
import { UserService } from '../../services/user.service';

export interface UserState {
    users: { [key: string]: User };
    me: string;
    loading: boolean;
}

const initialState:UserState = {
    users:{},
    me:undefined,
    loading:false,
}

export const userReducer = createReducer(initialState,
    on(userRegister, state => ({ ...state, loading: true })),
    on(userRegisterFail, state => ({ ...state, loading: false })),
    on(userRegisterSuccess, (state, { user }) => ({ ...state, loading: false, me: user.username, users: addUser(user, state.users) })),
);


function addUser(user: User, users: { [key: string]: User }): { [key: string]: User } {
    return { ...users, [user.username]: user };
}
