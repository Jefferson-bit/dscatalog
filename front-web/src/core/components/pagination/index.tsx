import React from 'react'
import { ReactComponent as SetaIcon } from 'core/assets/images/Seta.svg'
import './styles.scss'
import { generateList } from 'core/utils/list'


type Props = {
    totalPages: number;
    activePage: number;
    onChange: (item: number) => void;
}

const Pagination = ({totalPages, activePage, onChange} : Props) => {
    const items = generateList(totalPages);
    const previousClass = totalPages >0 && activePage >0 ? 'page-active' : 'page-inactive';
    const nextclass = (activePage +1) < totalPages ? 'page-active' : 'page-inactive';
    return (
        <div className="pagination-container">
            <SetaIcon 
            className={`pagination-previous ${previousClass}`} 
            onClick={() => onChange(activePage -1)} />

            {items.map(item => (
                <div key={item} className={`pagination-item ${item === activePage ? 'active': ''}`}
                onClick={() => onChange(item)}>
                    {item + 1}
                </div>
            ))}
            <SetaIcon 
            className={`pagination-next ${nextclass}`} 
            onClick={() => onChange(activePage +1)} />
        </div>
    )
}

export default Pagination;