import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faMoneyBillWave, faBoxes, faSave } from "@fortawesome/free-solid-svg-icons";

const UpdateQuotationForm = () => {
  const { quotationId } = useParams();
  const [quotation, setQuotation] = useState(null);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    const fetchQuotation = async () => {
      try {
        const response = await UserService.getquotationbyid(quotationId);
        setQuotation(response.data);
      } catch (error) {
        console.error(`Error fetching quotation with ID ${quotationId}:`, error);
        setError("Error fetching quotation. Please try again later.");
      }
    };

    fetchQuotation();
  }, [quotationId]);

  const handleUpdateQuotation = async (event) => {
    event.preventDefault();
    try {
      await UserService.updatequotation(quotation);
      setSuccess(true);
      setError(null);
      // Optionally, you can redirect after a short delay
      // setTimeout(() => {
      //   window.location.href = "/get-quotation";
      // }, 2000);
    } catch (error) {
      console.error(`Error updating quotation with ID ${quotationId}:`, error);
      setError("Error updating quotation. Please try again later.");
      setSuccess(false);
    }
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setQuotation(prevQuotation => ({
      ...prevQuotation,
      [name]: value
    }));
  };

  if (!quotation) {
    return <p>Loading...</p>;
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
            <div className="card-header text-white" style={{ backgroundColor: "#87ceeb" }}>
              <h2 className="mb-0">
                <FontAwesomeIcon icon={faEdit} className="mr-2" /> Apply Discount
              </h2>
            </div>
            <div className="card-body">
              <form onSubmit={handleUpdateQuotation}>
                <div className="form-group mb-4">
                  <label htmlFor="totalAmount" className="form-label">
                    <FontAwesomeIcon icon={faMoneyBillWave} className="mr-2" /> Total Amount:
                  </label>
                  <input
                    type="number"
                    id="totalAmount"
                    name="totalAmount"
                    className="form-control"
                    value={quotation.totalAmount}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group mb-4">
                  <label htmlFor="quantity" className="form-label">
                    <FontAwesomeIcon icon={faBoxes} className="mr-2" /> Quantity:
                  </label>
                  <input
                    type="number"
                    id="quantity"
                    name="quantity"
                    className="form-control"
                    value={quotation.quantity}
                    onChange={handleChange}
                    required
                  />
                </div>
                <button type="submit" className="btn btn-primary btn-lg w-100">
                  <FontAwesomeIcon icon={faSave} className="mr-2" /> Update Quotation
                </button>
              </form>
              {success && (
                <div className="alert alert-success mt-3" role="alert">
                  <FontAwesomeIcon icon={faSave} className="mr-2" /> Quotation updated successfully.
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

export default UpdateQuotationForm;