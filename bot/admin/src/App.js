import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Provider } from 'react-redux';
import store from './store';
import SendMessageContainer from './containers/sendMessageContainer';

export const App = () => {
  return (
    <Provider store={store}>
      <SendMessageContainer />
    </Provider>
  );
}