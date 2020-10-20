import NavBar from './components/navbar';
import React from 'react';
import './styles.scss'
import { Route, Switch } from 'react-router-dom';
import Products from './components/products';


const Admin = () => (
    <div className="admin-container">
        <NavBar />
        <div className="admin-content">
            <Switch>
                <Route path="/admin/products">
                    <Products />
                </Route>
                <Route path="/admin/categories">
                    <h1>Categorias</h1>
                </Route>
                <Route path="/admin/users">
                    <h1>Usuário</h1>
                </Route>
            </Switch>

        </div>  
    </div>
);

export default Admin;