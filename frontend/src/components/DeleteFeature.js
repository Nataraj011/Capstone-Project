import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashAlt, faBoxOpen, faCubes } from "@fortawesome/free-solid-svg-icons";

const DeleteFeature = () => {
  const [productId, setProductId] = useState("");
  const [featureId, setFeatureId] = useState("");
  const [products, setProducts] = useState([]);
  const [features, setFeatures] = useState([]);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await UserService.getall();
        setProducts(response.data);
      } catch (error) {
        console.error("Error fetching products:", error);
        setError("Error fetching products. Please try again later.");
      }
    };

    fetchProducts();
  }, []);

  useEffect(() => {
    const fetchFeatures = async () => {
      try {
        if (productId) {
          const response = await UserService.getfeaturebyproductidadmin(productId);
          setFeatures(response.data);
        } else {
          setFeatures([]);
        }
      } catch (error) {
        console.error("Error fetching features:", error);
        setError("Error fetching features. Please try again later.");
      }
    };

    fetchFeatures();
  }, [productId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await UserService.delFeatures(featureId);
      setSuccess(true);
      setError(null);
      setFeatureId("");
      const updatedFeatures = features.filter((feat) => feat.id !== parseInt(featureId));
      setFeatures(updatedFeatures);
    } catch (error) {
      console.error("Error deleting feature:", error);
      if (error.response && error.response.status === 404) {
        setError("Feature not found.");
      } else {
        setError("Error deleting feature.");
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
            <div className="card-header text-white" style={{ backgroundColor: "#87ceeb" }}>
              <h2 className="mb-0">
                <FontAwesomeIcon icon={faTrashAlt} className="mr-2" /> Delete Feature
              </h2>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="form-group mb-4">
                  <label htmlFor="productId" className="form-label">
                    <FontAwesomeIcon icon={faBoxOpen} className="mr-2" /> Product Name:
                  </label>
                  <select
                    className="form-select"
                    id="productId"
                    value={productId}
                    onChange={(e) => {
                      setProductId(e.target.value);
                      setFeatures([]);
                    }}
                    required
                  >
                    <option value="">-- Select Product --</option>
                    {products.map((product) => (
                      <option key={product.id} value={product.id}>
                        {product.name}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="form-group mb-4">
                  <label htmlFor="featureId" className="form-label">
                    <FontAwesomeIcon icon={faCubes} className="mr-2" /> Product Feature:
                  </label>
                  <select
                    className="form-select"
                    id="featureId"
                    value={featureId}
                    onChange={(e) => setFeatureId(e.target.value)}
                    required
                    disabled={!productId}
                  >
                    <option value="">-- Select Feature --</option>
                    {features.map((feature) => (
                      <option key={feature.id} value={feature.id}>
                        {feature.name}
                      </option>
                    ))}
                  </select>
                </div>
                <button type="submit" className="btn btn-danger btn-lg w-100">
                  <FontAwesomeIcon icon={faTrashAlt} className="mr-2" /> Delete Feature
                </button>
              </form>
              {success && (
                <div className="alert alert-success mt-3" role="alert">
                  <FontAwesomeIcon icon={faTrashAlt} className="mr-2" /> Feature deleted successfully.
                </div>
              )}
              {error && (
                <div className="alert alert-danger mt-3" role="alert">
                  <strong>Error:</strong> {error}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteFeature;