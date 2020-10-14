import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ProductResponse } from '../../core/types/Product';
import { makeRequest } from '../../core/utils/request';
import ProductCard from './components/product-card';
import './styles.scss';


const Catalog = () => {
    const [productsResponse, setProductsResponse] = useState<ProductResponse>();

    console.log(productsResponse)
    useEffect(() => {
        const params = {
            page: 0,
            linesPerPage: 12
        }
        makeRequest({ url: '/products', params })
            .then(response => setProductsResponse(response.data));
    }, []);

    return (
        <div className="clataog-container">
            <h1 className="catalog-title">
                Catalogo de Produtos
        </h1>
            <div className="catalog-products">
                {productsResponse?.content.map(product => (
                    <Link to={`/products/${product.id}`} key={product.id}>
                        <ProductCard product={product}/>
                    </Link>
                ))}
            </div>
        </div>
    )
};

export default Catalog;