import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import UserService from "../services/user.service";
import "./ViewAllQuotation.css"; // Assuming you have a CSS file for styling

const ViewAllQuotation = () => {
  const [quotations, setQuotations] = useState([]);
  const [error, setError] = useState(null);
  const [productsMap, setProductsMap] = useState({});
  const [featuresMap, setFeaturesMap] = useState({});

  useEffect(() => {
    const fetchQuotations = async () => {
      try {
        const response = await UserService.getallquotations();
        setQuotations(response.data);

        // Extract unique product and feature IDs
        const productIds = [...new Set(response.data.map(quotation => quotation.productId))];
        const featureIds = [...new Set(response.data.map(quotation => quotation.featureId))];

        // Fetch product names
        const productPromises = productIds.map(productId =>
          UserService.getproductbyidmgr(productId).then(response => ({
            id: productId,
            name: response.data.name
          }))
        );
        const products = await Promise.all(productPromises);
        const productsMap = products.reduce((map, product) => {
          map[product.id] = product.name;
          return map;
        }, {});
        setProductsMap(productsMap);

        // Fetch feature names
        const featurePromises = featureIds.map(featureId =>
          UserService.getfeaturebyid(featureId).then(response => ({
            id: featureId,
            name: response.data.name
          }))
        );
        const features = await Promise.all(featurePromises);
        const featuresMap = features.reduce((map, feature) => {
          map[feature.id] = feature.name;
          return map;
        }, {});
        setFeaturesMap(featuresMap);

      } catch (error) {
        console.error("Error fetching quotations:", error);
        setError("Error fetching quotations. Please try again later.");
      }
    };

    fetchQuotations();
  }, []);

  const handleDeleteQuotation = async (quotationId) => {
    try {
      // Make an API call to delete the quotation by ID
      await UserService.delQuotation(quotationId);

      // Update quotations state by filtering out the deleted quotation
      setQuotations(quotations.filter(quotation => quotation.id !== quotationId));
    } catch (error) {
      console.error(`Error deleting quotation with ID ${quotationId}:`, error);
      setError("Error deleting quotation. Please try again later.");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-10">
          <div className="card p-4 rounded">
            <h2 className="mb-4">All Quotations</h2>
            <div className="table-responsive">
              <table className="table table-striped table-bordered">
                <thead className="thead-dark">
                  <tr>
                    <th>Product Name</th>
                    <th>Feature Name</th>
                    <th>Total Amount</th>
                    <th>Quantity</th>
                    <th>Apply Discount</th>
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody>
                  {quotations.map((quotation) => (
                    <tr key={quotation.id}>
                      <td>{productsMap[quotation.productId]}</td>
                      <td>{featuresMap[quotation.featureId]}</td>
                      <td>{quotation.totalAmount}</td>
                      <td>{quotation.quantity}</td>
                      <td>
                        <Link to={`/update-quotation/${quotation.id}`} className="btn btn-primary btn-sm mr-2">
                          Apply Discount
                        </Link>
                      </td>
                      <td>
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => handleDeleteQuotation(quotation.id)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            {error && <p className="text-danger mt-3">{error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewAllQuotation;
