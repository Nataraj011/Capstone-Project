import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPlus,
  faList,
  faSearch,
  faEdit,
  faTrash,
  faUsers,
} from "@fortawesome/free-solid-svg-icons";
import "./BoardAdmin.css";

const BoardAdmin = () => {
  const navigateTo = (path) => {
    window.location.href = path;
  };

  return (
    <div className="admin-container">
      <div className="admin-sidebar">
        <h2 className="admin-logo">Admin Panel</h2>
        <ul className="admin-nav">
          <li className="admin-nav-item" onClick={() => navigateTo("/add-products")}>
            <FontAwesomeIcon icon={faPlus} />
            <span>Add Product and Features</span>
          </li>
          <li className="admin-nav-item" onClick={() => navigateTo("/view-product")}>
            <FontAwesomeIcon icon={faList} />
            <span>View Products</span>
          </li>
          <li className="admin-nav-item" onClick={() => navigateTo("/view-productbyname-admin")}>
            <FontAwesomeIcon icon={faSearch} />
            <span>View Products By Name</span>
          </li>
          <li className="admin-nav-item" onClick={() => navigateTo("/update-product")}>
            <FontAwesomeIcon icon={faEdit} />
            <span>Update Products</span>
          </li>
          <li className="admin-nav-item" onClick={() => navigateTo("/delete-product")}>
            <FontAwesomeIcon icon={faTrash} />
            <span>Delete Product</span>
          </li>
          <li className="admin-nav-item" onClick={() => navigateTo("/delete-feature")}>
            <FontAwesomeIcon icon={faTrash} />
            <span>Delete Features</span>
          </li>
          <li className="admin-nav-item" onClick={() => navigateTo("/updateroles")}>
            <FontAwesomeIcon icon={faUsers} />
            <span>User List</span>
          </li>
        </ul>
      </div>
      <div className="admin-content">
        <header className="admin-header">
          <h1 className="admin-heading">Admin Dashboard</h1>
        </header>
        <main className="admin-main">
          {/* Add your main content here */}
          <p>Welcome to the admin dashboard. Select an option from the sidebar to get started.</p>
        </main>
      </div>
    </div>
  );
};

export default BoardAdmin;