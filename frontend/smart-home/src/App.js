import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import './App.css';

import SignInPage from './pages/signInPage';
import DashboardMainPage from './pages/dashboardMainPage';

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={SignInPage} />
        <Route path="/dashboard" component={DashboardMainPage} />
      </Switch>
    </BrowserRouter>
  )
}

export default App;
