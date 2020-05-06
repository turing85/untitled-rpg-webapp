import { userReducer } from "./user.reducer";
import * as UserActions from './user.action';
import { User } from 'src/app/model/user/user.keycloak.model';

describe('UserReducer', () => {

    const initialState = { entities: {}, ids: [], loading: false, me: undefined };
    it('should stay in initial state with unknown Action', () => {
        const action = { type: 'foo' } as any;
        expect(userReducer(undefined, action)).toEqual(initialState);
    });

    //RegisterUser
    it('should change into loading state with userRegister action', () => {
        expect(userReducer(undefined, UserActions.userRegister)).toEqual({ ...initialState, loading: true });
    });

    it('should end loading with userRegisterFail action', () => {
        expect(userReducer({ ...initialState, loading: true }, UserActions.userRegisterFail)).toEqual({ ...initialState });
    });

    it('should store user withuserRegisterSuccess action', () => {
        const action = UserActions.userRegisterSuccess({ user: { name: 'a' } as User });
        expect(userReducer({ ...initialState, loading: true }, action)).toEqual({
            ...initialState,
            entities: { 'a': { name: 'a' } },
            ids: ['a'],
            me: 'a'
        });
    });
    //LoadUser
    it('should change into loading state with userLoad action', () => {
        expect(userReducer(undefined, UserActions.userLoad)).toEqual({ ...initialState, loading: true });
    });

    it('should end loading with userLoadFail action', () => {
        expect(userReducer({ ...initialState, loading: true }, UserActions.userLoadFail)).toEqual({ ...initialState });
    });

    it('should store user with userLoadSuccess action', () => {
        const action = UserActions.userLoadSuccess({ user: { name: 'a' } as User });
        expect(userReducer({ ...initialState, loading: true }, action)).toEqual({
            ...initialState,
            entities: { 'a': { name: 'a' } },
            ids: ['a']
        });
    });


    //LoadMe
    it('should change into loading state with userLoadMe action', () => {
        expect(userReducer(undefined, UserActions.userLoadMe)).toEqual({ ...initialState, loading: true });
    });

    it('should store user with userLoadMeSuccess action', () => {
        const action = UserActions.userLoadMeSuccess({ user: { name: 'a' } as User });
        expect(userReducer({ ...initialState, loading: true }, action)).toEqual({
            ...initialState,
            entities: { 'a': { name: 'a' } },
            ids: ['a'],
            me: 'a'
        });
    });

    //LoadAll
    it('should change into loading state with userLoadAll action', () => {
        expect(userReducer(undefined, UserActions.userLoadAll)).toEqual({ ...initialState, loading: true });
    });

    it('should end loading with userLoadAllFail action', () => {
        expect(userReducer({ ...initialState, loading: true }, UserActions.userLoadAllFail)).toEqual({ ...initialState });
    });

    it('should store user with userLoadSuccess action', () => {
        const action = UserActions.userLoadAllSuccess({
            users: [
                { name: 'a' } as User,
                { name: 'b' } as User
            ]
        });
        expect(userReducer({ ...initialState, loading: true }, action)).toEqual({
            ...initialState,
            entities: { 
                'a': { name: 'a' },
                'b': { name: 'b' }
            },
            ids: ['a','b']
        });
    });


});