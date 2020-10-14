import React from 'react'
import ContentLoader from 'react-content-loader'
import { generateList } from 'core/utils/list'

const ProductCardLoader = () => {
    const loaderItems = generateList(5);
    return (
        <>
        {loaderItems.map(item =>(
            <ContentLoader
            key={item}
            speed={1}
            width={250}
            height={313}
            viewBox="0 0 250 313"
            backgroundColor="#ecebeb"
            foregroundColor="#d6d2d2"
        >
            <rect x="0" y="0" rx="10" ry="10" width="250" height="313" ></rect>
        </ContentLoader>
        ))}

        </>
    )
}

export default ProductCardLoader