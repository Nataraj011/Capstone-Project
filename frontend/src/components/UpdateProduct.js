import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faEdit, 
  faCube, 
  faPuzzlePiece, 
  faWrench, 
  faSave 
} from "@fortawesome/free-solid-svg-icons";
import "./UpdateProduct.css";

const UpdateProduct = ({ product, onProductUpdate, products }) => {
  const [updatedProduct, setUpdatedProduct] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (product) {
      setUpdatedProduct({
        id: product.id,
        name: product.name,
        features: product.features.map((feature) => ({
          id: feature.id,
          name: feature.name,
          product: {
            id: feature.product ? feature.product.id : product.id,
            name: feature.product ? feature.product.name : product.name,
          },
          parameters: feature.parameters.map((param) => ({
            id: param.id,
            name: param.name,
            type: param.type,
            value: param.value,
          })),
        })),
      });
    }
  }, [product]);

  const handleProductNameChange = (featureIndex, newName) => {
    const selectedProduct = products.find((prod) => prod.name === newName);
    if (!selectedProduct) return;

    setUpdatedProduct((prevProduct) => ({
      ...prevProduct,
      features: prevProduct.features.map((feature, index) => {
        if (index !== featureIndex) return feature;
        return {
          ...feature,
          product: {
            id: selectedProduct.id,
            name: newName,
          },
        };
      }),
    }));
  };

  const handleFeatureNameChange = (featureIndex, newValue) => {
    setUpdatedProduct((prevProduct) => ({
      ...prevProduct,
      features: prevProduct.features.map((feature, index) => {
        if (index !== featureIndex) return feature;
        return { ...feature, name: newValue };
      }),
    }));
  };

  const handleParameterChange = (featureIndex, parameterIndex, newValue) => {
    setUpdatedProduct((prevProduct) => ({
      ...prevProduct,
      features: prevProduct.features.map((feature, index) => {
        if (index !== featureIndex) return feature;
        return {
          ...feature,
          parameters: feature.parameters.map((param, pIndex) => {
            if (pIndex !== parameterIndex) return param;
            return { ...param, value: newValue };
          }),
        };
      }),
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onProductUpdate(updatedProduct);
  };

  if (!updatedProduct) {
    return null;
  }

  return (
    <div className="update-product-container">
      <div className="update-product-card">
        <form onSubmit={handleSubmit}>
          <h3 className="update-product-title">
            <FontAwesomeIcon icon={faEdit} className="icon-spacing" />
            Update Product
          </h3>
          <div className="form-group">
            <label htmlFor="productName" className="form-label">
              <FontAwesomeIcon icon={faCube} className="icon-spacing" />
              Product Name:
            </label>
            <input
              type="text"
              id="productName"
              value={updatedProduct.name}
              onChange={(e) =>
                setUpdatedProduct((prevProduct) => ({
                  ...prevProduct,
                  name: e.target.value,
                }))
              }
              className="form-control"
              required
            />
          </div>

          <hr className="divider" />
          <h4 className="features-title">
            <FontAwesomeIcon icon={faPuzzlePiece} className="icon-spacing" />
            Updating Features
          </h4>

          {updatedProduct.features.map((feature, featureIndex) => (
            <div key={feature.id} className="feature-container">
              <h5 className="feature-subtitle">
                <FontAwesomeIcon icon={faWrench} className="icon-spacing" />
                Feature Name:
              </h5>
              <input
                type="text"
                value={feature.name}
                onChange={(e) =>
                  handleFeatureNameChange(featureIndex, e.target.value)
                }
                className="form-control"
                required
              />
              <h5 className="feature-subtitle">Product:</h5>
              <select
                value={feature.product.name}
                onChange={(e) =>
                  handleProductNameChange(featureIndex, e.target.value)
                }
                className="form-control"
                required
              >
                {products.map((prod) => (
                  <option key={prod.id} value={prod.name}>
                    {prod.name}
                  </option>
                ))}
              </select>
              {feature.parameters.map((param, parameterIndex) => (
                <div key={param.id} className="parameter-container">
                  <label
                    htmlFor={`param-${featureIndex}-${parameterIndex}`}
                    className="form-label"
                  >
                    {param.name}:
                  </label>
                  <input
                    type="text"
                    id={`param-${featureIndex}-${parameterIndex}`}
                    value={param.value}
                    onChange={(e) =>
                      handleParameterChange(
                        featureIndex,
                        parameterIndex,
                        e.target.value
                      )
                    }
                    className="form-control"
                    required
                  />
                </div>
              ))}
            </div>
          ))}
          <button type="submit" className="btn btn-primary update-btn">
            <FontAwesomeIcon icon={faSave} className="icon-spacing" />
            Update Product
          </button>
        </form>
      </div>
    </div>
  );
};

export default UpdateProduct;