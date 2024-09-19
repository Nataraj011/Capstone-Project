import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faList, faSearch } from "@fortawesome/free-solid-svg-icons";
import "./UserBoard.css";

const UserBoard = () => {
  const navigateTo = (path) => {
    window.location.href = path;
  };

  return (
    <div className="user-container">
      <div className="user-sidebar">
        <h2 className="sidebar-title">User Menu</h2>
        <ul className="user-nav">
          <li className="user-nav-item" onClick={() => navigateTo("/view-all-products")}>
            <FontAwesomeIcon icon={faList} />
            <span>View All Products</span>
          </li>
          <li className="user-nav-item" onClick={() => navigateTo("/view-products-by-name")}>
            <FontAwesomeIcon icon={faSearch} />
            <span>View Products by Name</span>
          </li>
        </ul>
      </div>
      <div className="user-content">
        <header className="user-header">
          <h1 className="user-heading">User Dashboard</h1>
        </header>
        <div className="dashboard-summary">
          <h2>Welcome to your user dashboard!</h2>
          <p>Here you can view product information and browse our catalog.</p>
        </div>
      </div>
    </div>
  );
};

export default UserBoard;