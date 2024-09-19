import React from "react";
import AuthService from "../services/auth.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faEnvelope, faIdCard, faShield } from "@fortawesome/free-solid-svg-icons";
import "./Profile.css"; // We'll create this CSS file

const Profile = () => {
  const currentUser = AuthService.getCurrentUser();

  return (
    <div className="profile-container">
      <div className="profile-card">
        <div className="profile-header">
          <FontAwesomeIcon icon={faUser} className="profile-icon" />
          <h2>{currentUser.username}'s Profile</h2>
        </div>
        <div className="profile-body">
          <div className="profile-info">
            <FontAwesomeIcon icon={faIdCard} className="info-icon" />
            <p><strong>Id:</strong> {currentUser.id}</p>
          </div>
          <div className="profile-info">
            <FontAwesomeIcon icon={faEnvelope} className="info-icon" />
            <p><strong>Email:</strong> {currentUser.email}</p>
          </div>
          <div className="profile-info">
            <FontAwesomeIcon icon={faShield} className="info-icon" />
            <div>
              <strong>Authorities:</strong>
              <ul className="authorities-list">
                {Array.isArray(currentUser.role) ? (
                  currentUser.role.map((role, index) => (
                    <li key={index}>{role}</li>
                  ))
                ) : (
                  <li>{currentUser.role}</li>
                )}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;