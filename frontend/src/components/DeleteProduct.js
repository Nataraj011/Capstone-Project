import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashAlt, faBoxOpen } from "@fortawesome/free-solid-svg-icons";

const DeleteProduct = () => {
  const [products, setProducts] = useState([]);
  const [selectedProductId, setSelectedProductId] = useState('');
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    async function fetchProducts() {
      try {
        const response = await UserService.getall();
        setProducts(response.data);
      } catch (error) {
        console.error('Error fetching products:', error);
        setError("Error fetching products. Please try again later.");
      }
    }

    fetchProducts();
  }, [success]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await UserService.delProduct(selectedProductId);
      setSuccess(true);
      setError(null);
      setSelectedProductId('');
    } catch (error) {
      console.error('Error deleting product:', error);
      if (error.response && error.response.status === 404) {
        setError('Product not found.');
      } else if (error.response && error.response.status === 500) {
        setError('Product with quotations cannot be deleted.');
      } else {
        setError('Error deleting product');
      }
    }
  };

  const handleDropdownChange = (e) => {
    setSelectedProductId(e.target.value);
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
            <div className="card-header text-white" style={{ backgroundColor: "#87ceeb" }}>
              <h2 className="mb-0">
                <FontAwesomeIcon icon={faTrashAlt} className="mr-2" /> Delete Product
              </h2>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="form-group mb-4">
                  <label htmlFor="productDropdown" className="form-label">
                    <FontAwesomeIcon icon={faBoxOpen} className="mr-2" /> Select Product:
                  </label>
                  <select
                    id="productDropdown"
                    className="form-select"
                    value={selectedProductId}
                    onChange={handleDropdownChange}
                    required
                  >
                    <option value="">-- Select Product --</option>
                    {products.map(product => (
                      <option key={product.id} value={product.id}>{product.name}</option>
                    ))}
                  </select>
                </div>
                <button type="submit" className="btn btn-danger btn-lg w-100">
                  <FontAwesomeIcon icon={faTrashAlt} className="mr-2" /> Delete Product
                </button>
              </form>
              {success && (
                <div className="alert alert-success mt-3" role="alert">
                  <FontAwesomeIcon icon={faTrashAlt} className="mr-2" /> Product deleted successfully.
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

export default DeleteProduct;