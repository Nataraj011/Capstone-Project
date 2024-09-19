import React, { useState, useEffect } from "react";
import { TransitionGroup, CSSTransition } from "react-transition-group";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faBox, 
  faPuzzlePiece, 
  faList, 
  faExclamationTriangle 
} from "@fortawesome/free-solid-svg-icons";
import UserService from "../services/user.service";
import "./Allproducts.css";

const ViewAllProducts = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  const getAllProducts = async () => {
    try {
      const response = await UserService.getall();
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
      setError('Error fetching products');
    }
  };

  useEffect(() => {
    getAllProducts();
  }, []);

  return (
    <div className="products-container">
      <h2 className="products-title">
        <FontAwesomeIcon icon={faBox} /> Our Products
      </h2>
      {error && (
        <p className="error-message">
          <FontAwesomeIcon icon={faExclamationTriangle} /> {error}
        </p>
      )}
      <div className="products-grid">
        <TransitionGroup component={null}>
          {products.map((product) => (
            <CSSTransition key={product.id} classNames="fade" timeout={500}>
              <div className="product-card">
                <div className="product-header">
                  <h3 className="product-title">
                    <FontAwesomeIcon icon={faBox} /> {product.name}
                  </h3>
                </div>
                <div className="product-body">
                  {product.features.map((feature) => (
                    <div key={feature.id} className="feature-section">
                      <h4 className="feature-title">
                        <FontAwesomeIcon icon={faPuzzlePiece} /> {feature.name}
                      </h4>
                      <h5 className="parameters-title">
                        <FontAwesomeIcon icon={faList} /> Parameters
                      </h5>
                      <ul className="parameters-list">
                        {feature.parameters.map((parameter) => (
                          <li key={parameter.id} className="parameter-item">
                            <span className="parameter-name">{parameter.name}:</span>
                            <span className="parameter-value">{parameter.value}</span>
                            <span className="parameter-type">({parameter.type})</span>
                          </li>
                        ))}
                      </ul>
                    </div>
                  ))}
                </div>
              </div>
            </CSSTransition>
          ))}
        </TransitionGroup>
      </div>
    </div>
  );
};

export default ViewAllProducts;