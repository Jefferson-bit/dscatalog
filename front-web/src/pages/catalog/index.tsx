import React from 'react';
import ProductCard from './components/product-card';
import './styles.scss';


const Catalog = () => (
    <div className="clataog-container">
        <h1 className="catalog-title">
            Catalogo de Produtos
        </h1>
            <div className="catalog-products">
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
                <ProductCard />
        </div>
    </div>
);

export default Catalog;