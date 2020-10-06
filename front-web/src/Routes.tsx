import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import NavBar from './core/components/navbar';
import Admin from './pages/admin';
import Catalog from './pages/catalog';
import Home from './pages/home';


const Routes = () =>  (
    <BrowserRouter>
        <NavBar />
        <Switch>
            <Route path="/" exact> 
                <Home />
            </Route>
            <Route path="/catalog"> 
                <Catalog />
            </Route>
            <Route path="/admin"> 
                <Admin/>
            </Route>
        </Switch>
    </BrowserRouter>
);

export default Routes;