import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faFileInvoiceDollar, 
  faBoxOpen, 
  faPuzzlePiece, 
  faDollarSign, 
  faShoppingCart, 
  faCalculator, 
  faPlus, 
  faCheckCircle, 
  faExclamationTriangle 
} from "@fortawesome/free-solid-svg-icons";
import "./AddQuotation.css";

const AddQuotation = () => {
  const [formData, setFormData] = useState({
    productId: "",
    featureId: "",
    amount: 0,
    quantity: 0,
    totalAmount: 0,
  });
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
        if (formData.productId) {
          const response = await UserService.getfeaturebyproductid(formData.productId);
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
  }, [formData.productId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    if (name === "amount" || name === "quantity") {
      calculateTotalAmount({ ...formData, [name]: parseFloat(value) });
    }
  };

  const calculateTotalAmount = ({ amount, quantity }) => {
    const total = parseFloat(amount) * parseInt(quantity);
    setFormData((prevData) => ({
      ...prevData,
      totalAmount: total.toFixed(2),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await UserService.addquotation(formData);
      setSuccess(true);
      setError(null);
    } catch (error) {
      console.error("Error adding quotation:", error);
      if (
        (error.response && error.response.status === 404) ||
        (error.response && error.response.status === 500)
      ) {
        setError("Product  is invalid.");
      } else {
        setError("Error adding quotation.");
      }
    }
  };

  return (
    <div className="add-quotation-container">
      <div className="add-quotation-card">
        <h2 className="add-quotation-title">
          <FontAwesomeIcon icon={faFileInvoiceDollar} className="icon-spacing" />
          Add Quotation
        </h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="productId">
              <FontAwesomeIcon icon={faBoxOpen} className="icon-spacing" />
              Product Name:
            </label>
            <select
              className="form-control"
              id="productId"
              name="productId"
              value={formData.productId}
              onChange={handleChange}
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
          <div className="form-group">
            <label htmlFor="featureId">
              <FontAwesomeIcon icon={faPuzzlePiece} className="icon-spacing" />
              Product Feature:
            </label>
            <select
              className="form-control"
              id="featureId"
              name="featureId"
              value={formData.featureId}
              onChange={handleChange}
              required
              disabled={!formData.productId}
            >
              <option value="">-- Select Feature --</option>
              {features.map((feature) => (
                <option key={feature.id} value={feature.id}>
                  {feature.name}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="amount">
              <FontAwesomeIcon icon={faDollarSign} className="icon-spacing" />
              Amount:
            </label>
            <input
              type="number"
              className="form-control"
              id="amount"
              name="amount"
              value={formData.amount}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="quantity">
              <FontAwesomeIcon icon={faShoppingCart} className="icon-spacing" />
              Quantity:
            </label>
            <input
              type="number"
              className="form-control"
              id="quantity"
              name="quantity"
              value={formData.quantity}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="totalAmount">
              <FontAwesomeIcon icon={faCalculator} className="icon-spacing" />
              Total Amount:
            </label>
            <input
              type="text"
              className="form-control"
              id="totalAmount"
              name="totalAmount"
              value={formData.totalAmount}
              readOnly
            />
          </div>
          <button type="submit" className="btn btn-primary add-btn">
            <FontAwesomeIcon icon={faPlus} className="icon-spacing" />
            Add Quotation
          </button>
        </form>
        {error && (
          <div className="alert alert-danger mt-3">
            <FontAwesomeIcon icon={faExclamationTriangle} className="icon-spacing" />
            {error}
          </div>
        )}
        {success && (
          <div className="alert alert-success mt-3">
            <FontAwesomeIcon icon={faCheckCircle} className="icon-spacing" />
            Quotation added successfully.
          </div>
        )}
      </div>
    </div>
  );
};

export default AddQuotation;