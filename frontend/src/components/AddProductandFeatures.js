import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faCube, faPuzzlePiece, faCogs } from "@fortawesome/free-solid-svg-icons";
import "./AddProductsandFeatures.css";

const AddProductAndFeatures = () => {
  // State for adding a product
  const [productName, setProductName] = useState('');
  const [addedProduct, setAddedProduct] = useState(null);
  const [productError, setProductError] = useState(null);

  // State for adding features
  const [productId, setProductId] = useState('');
  const [featureName, setFeatureName] = useState('');
  const [parameterName1, setParameterName1] = useState('');
  const [parameterType1, setParameterType1] = useState('');
  const [parameterValue1, setParameterValue1] = useState('');
  const [parameterName2, setParameterName2] = useState('');
  const [parameterType2, setParameterType2] = useState('');
  const [parameterValue2, setParameterValue2] = useState('');
  const [parameterName3, setParameterName3] = useState('');
  const [parameterType3, setParameterType3] = useState('');
  const [parameterValue3, setParameterValue3] = useState('');
  const [addedFeatures, setAddedFeatures] = useState(null);
  const [featureError, setFeatureError] = useState(null);

  // State for managing products fetched from API
  const [products, setProducts] = useState([]);

  // Fetch products when component mounts
  useEffect(() => {
    fetchProducts();
  }, []);

  // Function to handle adding a product
  const handleProductSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      id: 0,
      name: productName,
      features: null
    };

    try {
      const response = await UserService.addproduct(payload);
      setAddedProduct(response.data);
      setProductError(null);
      setProductName(''); // Reset product name input
      fetchProducts(); // Update products list
    } catch (error) {
      console.error('Error adding product:', error);
      setProductError('Error adding product');
    }
  };

  // Function to handle adding features
  const handleFeatureSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      id: 0,
      name: featureName,
      product: {
        id: productId,
        name: productName
      },
      parameters: [
        {
          id: 0,
          name: parameterName1,
          type: parameterType1,
          value: parameterValue1
        },
        {
          id: 0,
          name: parameterName2,
          type: parameterType2,
          value: parameterValue2
        },
        {
          id: 0,
          name: parameterName3,
          type: parameterType3,
          value: parameterValue3
        }
      ]
    };

    try {
      const response = await UserService.addFeatures(payload);
      setAddedFeatures(response.data);
      setFeatureError(null);
      // Reset all form fields after successful submission
      setFeatureName('');
      setProductId('');
      setProductName('');
      setParameterName1('');
      setParameterType1('');
      setParameterValue1('');
      setParameterName2('');
      setParameterType2('');
      setParameterValue2('');
      setParameterName3('');
      setParameterType3('');
      setParameterValue3('');
      fetchProducts(); // Update products list
    } catch (error) {
      console.error('Error adding features:', error);
      setFeatureError('Error adding features');
    }
  };

  // Function to handle product selection
  const handleProductChange = (e) => {
    const selectedProductId = e.target.value;
    const selectedProduct = products.find(product => product.id === parseInt(selectedProductId));

    if (selectedProduct) {
      setProductId(selectedProductId);
      setProductName(selectedProduct.name);
    }
  };

  // Function to fetch products
  const fetchProducts = async () => {
    try {
      const response = await UserService.getall();
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  return (
    <div className="add-product-features-container">
      <div className="form-card product-form">
        <h2 className="form-title">
          <FontAwesomeIcon icon={faCube} /> Add Product
        </h2>
        <form onSubmit={handleProductSubmit}>
          <div className="form-group">
            <label htmlFor="productName">
              <FontAwesomeIcon icon={faCube} /> Product Name:
            </label>
            <input
              type="text"
              id="productName"
              value={productName}
              onChange={(e) => setProductName(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn-submit">
            <FontAwesomeIcon icon={faPlus} /> Add Product
          </button>
        </form>
        {addedProduct && (
          <div className="success-message">
            <h3>Product added successfully:</h3>
            <p>Name: {addedProduct.name}</p>
          </div>
        )}
        {productError && <p className="error-message">Error: {productError}</p>}
      </div>

      <div className="form-card feature-form">
        <h2 className="form-title">
          <FontAwesomeIcon icon={faPuzzlePiece} /> Add Features
        </h2>
        <form onSubmit={handleFeatureSubmit}>
          <div className="form-group">
            <label htmlFor="productDropdown">
              <FontAwesomeIcon icon={faCube} /> Select Product:
            </label>
            <select
              id="productDropdown"
              onChange={handleProductChange}
              value={productId}
              required
            >
              <option value="">-- Select Product --</option>
              {products.map(product => (
                <option key={product.id} value={product.id.toString()}>{product.name}</option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="featureName">
              <FontAwesomeIcon icon={faPuzzlePiece} /> Feature Name:
            </label>
            <input
              type="text"
              id="featureName"
              value={featureName}
              onChange={(e) => setFeatureName(e.target.value)}
              required
            />
          </div>
          <div className="parameters-section">
            <h3><FontAwesomeIcon icon={faCogs} /> Parameters</h3>
            {[1, 2, 3].map((num) => (
              <div key={num} className="parameter-group">
                <div className="form-group">
                  <label htmlFor={`parameterName${num}`}>Parameter Name {num}:</label>
                  <input
                    type="text"
                    id={`parameterName${num}`}
                    value={eval(`parameterName${num}`)}
                    onChange={(e) => eval(`setParameterName${num}(e.target.value)`)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor={`parameterType${num}`}>Parameter Type {num}:</label>
                  <input
                    type="text"
                    id={`parameterType${num}`}
                    value={eval(`parameterType${num}`)}
                    onChange={(e) => eval(`setParameterType${num}(e.target.value)`)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor={`parameterValue${num}`}>Parameter Value {num}:</label>
                  <input
                    type="text"
                    id={`parameterValue${num}`}
                    value={eval(`parameterValue${num}`)}
                    onChange={(e) => eval(`setParameterValue${num}(e.target.value)`)}
                    required
                  />
                </div>
              </div>
            ))}
          </div>
          <button type="submit" className="btn-submit">
            <FontAwesomeIcon icon={faPlus} /> Add Features
          </button>
        </form>
        {addedFeatures && (
          <div className="success-message">
            <h3>Features added successfully:</h3>
            <p>Feature ID: {addedFeatures.id}</p>
            <p>Feature Name: {addedFeatures.name}</p>
          </div>
        )}
        {featureError && <p className="error-message">Error: {featureError}</p>}
      </div>
    </div>
  );
};

export default AddProductAndFeatures;