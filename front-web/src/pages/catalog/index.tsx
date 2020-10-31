import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ProductResponse } from 'core/types/Product';
import { makeRequest } from 'core/utils/request';
import ProductCardLoader from './components/Loader/ProductCardLoader';
import ProductCard from './components/product-card';
import './styles.scss';
import Pagination from 'core/components/pagination';

const Catalog = () => {
    const [productsResponse, setProductsResponse] = useState<ProductResponse>();
    const [isLoading, setIsLoading] = useState(false);
    const [activePage, setActivePage] = useState(0);

    useEffect(() => {
        const params = {
            page: activePage,
            linesPerPage: 12
        }
        setIsLoading(true)
        makeRequest({ url: '/products', params })
            .then(response => setProductsResponse(response.data))
            .finally(() => {
                setIsLoading(false)
            })
    }, [activePage]);

    return (
        <div className="clataog-container">
            <h1 className="catalog-title">
                Catalogo de Produtos
        </h1>
            <div className="catalog-products">
                {isLoading ? <ProductCardLoader /> : (
                    productsResponse?.content.map(product => (
                        <Link to={`/products/${product.id}`} key={product.id}>
                            <ProductCard product={product}/>
                        </Link>
                    ))
                )}
            </div>
            {productsResponse && (
            <Pagination
            totalPages={productsResponse?.totalPages} 
            activePage={activePage}
            onChange={page => setActivePage(page)}
            />
            )}
        </div>
    )
};

export default Catalog;