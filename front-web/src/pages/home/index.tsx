import React from 'react';
import { ReactComponent as MainImage} from '../../core/assets/images/Desenho (1).svg';
import ButtonIcon from '../../core/components/button-icon';
import { Link }  from 'react-router-dom';
import './styles.scss';
const Home = () => (
    <div className="home-container">
        <div className="row home-content card-base border-radius-20">
            <div className="col-6">
                <h1 className="text-title">Conheça o melhor <br />catálogo de produtos</h1>
                <p className="text-subtitle">Ajudaremos você a encontrar os melhores<br /> produtos disponiveis no mercado</p>
                <Link to="/catalog">
                <ButtonIcon text="Inicie agora a sua busca" />
                </Link>
            </div>
            <div className="col-6">
                <MainImage className="main-image"/>
            </div>
        </div>
    </div>
);

export default Home;