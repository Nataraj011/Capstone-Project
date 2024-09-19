import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch, faCube, faList, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import "./ProductByName.css"; // We'll create this CSS file

const ProductByName = () => {
  const [productName, setProductName] = useState("");
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [error, setError] = useState(null);

  const fetchProducts = async () => {
    try {
      const response = await UserService.getall();
      if (response.data) {
        setProducts(response.data);
      } else {
        setProducts([]);
      }
    } catch (error) {
      console.error('Error fetching products:', error);
      setProducts([]);
      setError('Failed to fetch products.');
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const getProductByName = async () => {
    try {
      const response = await UserService.getproductbyname(productName);
      if (response.data) {
        setSelectedProduct(response.data);
        setError(null);
      } else {
        setSelectedProduct(null);
        setError('Product not found.');
      }
    } catch (error) {
      console.error('Error fetching product:', error);
      setSelectedProduct(null);
      if (error.response && error.response.status === 404) {
        setError('Product not found.');
      } else {
        setError('An unexpected error occurred.');
      }
    }
  };

  return (
    <div className="product-by-name-container">
      <div className="search-card">
        <h2><FontAwesomeIcon icon={faSearch} /> View Product By Name</h2>
        <div className="search-input">
          <select
            value={productName}
            onChange={(e) => setProductName(e.target.value)}
          >
            <option value="">Select a product</option>
            {products.map((product) => (
              <option key={product.id} value={product.name}>
                {product.name}
              </option>
            ))}
          </select>
          <button onClick={getProductByName}>
            <FontAwesomeIcon icon={faSearch} /> View Product
          </button>
        </div>
        {error && <p className="error-message"><FontAwesomeIcon icon={faInfoCircle} /> {error}</p>}
      </div>
      {selectedProduct && (
        <div className="product-details-card">
          <h3><FontAwesomeIcon icon={faCube} /> {selectedProduct.name}</h3>
          {selectedProduct.features && selectedProduct.features.length > 0 && (
            <div className="features-list">
              <h4><FontAwesomeIcon icon={faList} /> Features:</h4>
              {selectedProduct.features.map((feature) => (
                <div key={feature.id} className="feature-item">
                  <h5>{feature.name}</h5>
                  <ul>
                    {feature.parameters.map((parameter) => (
                      <li key={parameter.id}>
                        <strong>{parameter.name}</strong>: {parameter.value} ({parameter.type})
                      </li>
                    ))}
                  </ul>
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default ProductByName;