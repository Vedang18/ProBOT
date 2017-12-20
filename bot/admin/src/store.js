import { createStore, combineReducers, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import { createLogger } from 'redux-logger';
import promise from 'redux-promise-middleware';
import userReducer from './reducers/userReducer';
import sendMessageReducer from './reducers/sendMessageReducer';

export default createStore(
    combineReducers({
        userReducer,
        sendMessageReducer
    }),
    {},
    applyMiddleware(createLogger(), thunk, promise())
);