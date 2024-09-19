import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import UpdateProduct from "./UpdateProduct";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faSearch, faExclamationCircle } from "@fortawesome/free-solid-svg-icons";
import "./UpdateProductForm.css"; // Make sure to create this CSS file

const UpdateProductForm = () => {
  const [productId, setProductId] = useState('');
  const [product, setProduct] = useState(null);
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);
  const [showUpdateForm, setShowUpdateForm] = useState(false);
  const [responseMessage, setResponseMessage] = useState('');

  useEffect(() => {
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

    fetchProducts();
  }, []);

  const handleProductIdChange = (e) => {
    setProductId(e.target.value);
    setProduct(null);
    setShowUpdateForm(false);
    setResponseMessage('');
    setError(null);
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await UserService.getproductbyidadmin(productId);
      if (response.data) {
        setProduct(response.data);
        setShowUpdateForm(true);
        setError(null);
        setResponseMessage('Product details fetched successfully.');
      } else {
        setProduct(null);
        setShowUpdateForm(false);
        setError('Product not found.');
        setResponseMessage('');
      }
    } catch (error) {
      console.error('Error fetching product:', error);
      setProduct(null);
      setShowUpdateForm(false);
      if (error.response && error.response.status === 404) {
        setError('Product not found.');
      } else {
        setError('An unexpected error occurred.');
      }
      setResponseMessage('Error fetching product. Please try again.');
    }
  };

  const handleProductUpdate = async (updatedProduct) => {
    try {
      await UserService.updateproduct(updatedProduct);
      setShowUpdateForm(false);
      setResponseMessage('Product updated successfully.');
    } catch (error) {
      console.error('Error updating product:', error);
      setError('Error updating product');
      setResponseMessage('Error updating product. Please try again.');
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
          <div className="card-header">
  <h2 className="mb-0">
    <FontAwesomeIcon icon={faEdit} className="mr-2" /> Update Product and Features
  </h2>
</div>
            <div className="card-body">
              <form onSubmit={handleFormSubmit} className="mb-4">
                <div className="form-group">
                  <label htmlFor="productName" className="font-weight-bold">
                    <FontAwesomeIcon icon={faSearch} className="mr-2" /> Select Product:
                  </label>
                  <select
                    id="productName"
                    className="form-control form-control-lg"
                    value={productId}
                    onChange={handleProductIdChange}
                    required
                  >
                    <option value="">Select a product</option>
                    {products.map((prod) => (
                      <option key={prod.id} value={prod.id}>
                        {prod.name}
                      </option>
                    ))}
                  </select>
                </div>
                <button type="submit" className="btn btn-primary btn-lg btn-block">
                  <FontAwesomeIcon icon={faSearch} className="mr-2" /> Get Product Details
                </button>
              </form>
              
              {error && (
                <div className="alert alert-danger" role="alert">
                  <FontAwesomeIcon icon={faExclamationCircle} className="mr-2" /> {error}
                </div>
              )}
              
              {responseMessage && (
                <div className="alert alert-success" role="alert">
                  {responseMessage}
                </div>
              )}
              
              {showUpdateForm && product && (
                <div className="mt-4">
                  <UpdateProduct
                    product={product}
                    productId={productId}
                    onProductUpdate={handleProductUpdate}
                    products={products}
                  />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateProductForm;