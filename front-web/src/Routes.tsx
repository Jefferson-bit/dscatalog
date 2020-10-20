import React from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import NavBar from './core/components/navbar';
import Admin from './pages/admin';
import Catalog from './pages/catalog';
import ProductDetails from './pages/catalog/components/product-details';
import Home from './pages/home';


const Routes = () =>  (
    <BrowserRouter>
        <NavBar />
        <Switch>
            <Route path="/" exact> 
                <Home />
            </Route>
            <Route path="/products" exact> 
                <Catalog />
            </Route>
            <Route path="/products/:productId"> 
                <ProductDetails />
            </Route>
                <Redirect from="/admin"to="/admin/products" exact/>
            <Route path="/admin"> 
                <Admin/>
            </Route>
        </Switch>
    </BrowserRouter>
);

export default Routes;