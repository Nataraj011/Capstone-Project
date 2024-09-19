import React, { useState } from "react";
import UserService from "../services/user.service";

const ViewQuotation = () => {
  const [id, setId] = useState("");
  const [quotation, setQuotation] = useState(null);
  const [error, setError] = useState(null);
  const [lastQuotationId, setLastQuotationId] = useState(null);

  const handleIdChange = (e) => {
    setId(e.target.value);
  };

  const fetchQuotation = async () => {
    try {
      const response = await UserService.getquotation(id);
      setQuotation(response.data);
      setError(null);
      setLastQuotationId(id); // Store the last viewed quotation ID
    } catch (error) {
      console.error("Error fetching quotation:", error);
      if (error.response && error.response.status === 404) {
        setError("Quotation not found.");
      } else {
        setError("Error fetching quotation. Please try again.");
      }
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (id.trim() !== "") {
      fetchQuotation();
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card p-4 rounded">
            <h2 className="mb-4">View Quotation</h2>
            <form onSubmit={handleSubmit}>
              <div className="input-group mb-3">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Quotation ID"
                  value={id}
                  onChange={handleIdChange}
                />
                <div className="input-group-append">
                  <button
                    className="btn btn-outline-primary"
                    type="submit"
                  >
                    Fetch Quotation
                  </button>
                </div>
              </div>
            </form>
            {error && <p className="mt-2 text-danger">{error}</p>}
            {quotation && id === lastQuotationId && (
              <div className="mt-4">
                <h3>Quotation Details</h3>
                <p>ID: {quotation.id}</p>
                <p>User ID: {quotation.userId}</p>
                <p>Product ID: {quotation.productId}</p>
                <p>Total Amount: {quotation.totalAmount}</p>
                <p>Quantity: {quotation.quantity}</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewQuotation;
