import React, { useState } from "react";
import UserService from "../services/user.service";

const ViewProductsByIdadmin = () => {
  const [productId, setProductId] = useState("");
  const [product, setProduct] = useState(null);
  const [error, setError] = useState(null);

  // Function to fetch product by ID
  const getProductById = async () => {
    try {
      const response = await UserService.getproductbyidadmin(productId);
      if (response.data) {
        setProduct(response.data);
        setError(null); // Reset error state if product is found
      } else {
        setProduct(null); // Clear product if product is not found
        setError('Product not found.'); // Set error message if product is not found
      }
    } catch (error) {
      console.error('Error fetching product:', error);
      setProduct(null); // Clear product if error occurs
      if (error.response && error.response.status === 404) {
        setError('Product not found.'); // Set error message if product is not found (HTTP 404)
      } else {
        setError('An unexpected error occurred.'); // Set generic error message for other errors
      }
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card p-4 rounded">
            <h2 className="mb-4">View Product By ID</h2>
            <div className="input-group mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Enter product ID"
                value={productId}
                onChange={(e) => setProductId(e.target.value)}
              />
              <div className="input-group-append">
                <button
                  className="btn btn-outline-primary"
                  type="button"
                  onClick={getProductById}
                >
                  View Product
                </button>
              </div>
            </div>
            {error && <p className="mt-2 text-danger">{error}</p>}
            {product && (
              <div className="mt-4">
                <h3>Product: {product.name}</h3>
                {product.features && product.features.length > 0 && (
                  <div>
                    <h4>Features:</h4>
                    <ul className="list-group">
                      {product.features.map(feature => (
                        <li key={feature.id} className="list-group-item">
                          <strong>{feature.name}</strong>:
                          <ul className="list-group">
                            {feature.parameters.map(parameter => (
                              <li key={parameter.id} className="list-group-item">
                                <strong>{parameter.name}</strong>: {parameter.value} ({parameter.type})
                              </li>
                            ))}
                          </ul>
                        </li>
                      ))}
                    </ul>
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewProductsByIdadmin;
