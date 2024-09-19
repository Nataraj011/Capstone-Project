import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFileInvoiceDollar, faEye, faSearch, faList } from "@fortawesome/free-solid-svg-icons";
import "./ManagerBoard.css";

const ManagerBoard = () => {
  const navigateTo = (path) => {
    window.location.href = path;
  };

  return (
    <div className="manager-container">
      <div className="manager-sidebar">
        <h2 className="sidebar-title">Manager Menu</h2>
        <ul className="manager-nav">
          <li className="manager-nav-item" onClick={() => navigateTo("/create-quotation")}>
            <FontAwesomeIcon icon={faFileInvoiceDollar} />
            <span>Create Quotation</span>
          </li>
          <li className="manager-nav-item" onClick={() => navigateTo("/get-quotation")}>
            <FontAwesomeIcon icon={faEye} />
            <span>View Quotation</span>
          </li>
          <li className="manager-nav-item" onClick={() => navigateTo("/view-product-by-name-mgr")}>
            <FontAwesomeIcon icon={faSearch} />
            <span>View Products By Name</span>
          </li>
          <li className="manager-nav-item" onClick={() => navigateTo("/view-all-products-mgr")}>
            <FontAwesomeIcon icon={faList} />
            <span>View All Products</span>
          </li>
        </ul>
      </div>
      <div className="manager-content">
        <header className="manager-header">
          <h1 className="manager-heading">Manager Dashboard</h1>
        </header>
        <div className="dashboard-summary">
          <h2>Welcome to your dashboard!</h2>
          <p>Here you can manage quotations and view product information.</p>
        </div>
      </div>
    </div>
  );
};

export default ManagerBoard;