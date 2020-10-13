import React from 'react';
import './styles.scss';
import  {ReactComponent as ProductImage}from '../../../../core/assets/images/product.svg';
import ProductPrice from '../product-price';

const ProductDetails = () => (
    <div className="card-base border-radius-10 product-card">
        <ProductImage />
        <div className="product-info">
            <h6 className="product-name">
                Computador desktop - INTEL CORE I7
            </h6>
            <ProductPrice price="2.779,00"/>

        </div>
    </div>
);

export default ProductDetails;