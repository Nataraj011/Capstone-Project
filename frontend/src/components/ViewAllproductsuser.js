import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";


const ViewAllproductsuser = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  // Function to fetch all products
  const getAllProducts = async () => {
    try {
      const response = await UserService.getallbyuser();
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
      setError('Error fetching products');
    }
  };

  useEffect(() => {
    // Fetch all products when component mounts
    getAllProducts();
  }, []);

  return (
    <div className="container">
      <h2 className="mt-4">Our Products</h2>
      {error && <p className="text-danger mt-4">{error}</p>}
      <div className="row">
        {products.map(product => (
          <div key={product.id} className="col-md-6 mb-4">
            <div className="card">
              <div className="card-header">
                <h3 className="card-title">Product: {product.name}</h3>
              </div>
              <div className="card-body">
                {product.features.map(feature => (
                  <div key={feature.id} className="mb-3">
                    <h4>Feature: {feature.name}</h4>
                    <h5>Parameters</h5>
                    <ul className="list-unstyled">
                      {feature.parameters.map(parameter => (
                        <li key={parameter.id}>
                          {parameter.name}: {parameter.value} ({parameter.type})
                        </li>
                      ))}
                    </ul>
                  </div>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ViewAllproductsuser;
