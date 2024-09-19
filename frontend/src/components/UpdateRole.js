import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faUserEdit, 
  faUser, 
  faUserTag, 
  faSave, 
  faCheckCircle, 
  faExclamationTriangle 
} from "@fortawesome/free-solid-svg-icons";
import "./UpdateRole.css";

const UpdateRole = () => {
  const { userId } = useParams();
  const [selectedRole, setSelectedRole] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState("");

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await UserService.getalluser();
        setUsers(response.data);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    const fetchRoles = async () => {
      try {
        const response = await UserService.getallrole();
        setRoles(response.data);
      } catch (error) {
        console.error("Error fetching roles:", error);
      }
    };

    fetchUsers();
    fetchRoles();
  }, []);

  useEffect(() => {
    if (userId) {
      setSelectedUserId(userId);
    }
  }, [userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      const selectedUser = users.find((user) => user.id.toString() === selectedUserId);
      if (!selectedUser) {
        throw new Error("User not found.");
      }

      if (!selectedRole) {
        throw new Error("Please select a role.");
      }

      await UserService.updaterolebyuser(selectedUserId, selectedRole);
      setMessage("Role updated successfully!");
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setError("User not found.");
      } else {
        setError("An error occurred while updating the role.");
      }
    }
  };

  const handleRoleChange = (e) => {
    const roleId = e.target.value;
    const role = roles.find((r) => r.id.toString() === roleId);
    setSelectedRole(role);
  };

  return (
    <div className="update-role-container">
      <div className="update-role-card">
        <h2 className="update-role-title">
          <FontAwesomeIcon icon={faUserEdit} className="icon-spacing" />
          Update User Role
        </h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>
              <FontAwesomeIcon icon={faUser} className="icon-spacing" />
              User Username:
            </label>
            <input
              type="text"
              className="form-control"
              value={users.find(user => user.id === parseInt(selectedUserId))?.username || ""}
              disabled
            />
          </div>
          <div className="form-group">
            <label>
              <FontAwesomeIcon icon={faUserTag} className="icon-spacing" />
              Role:
            </label>
            <select
              className="form-control"
              value={selectedRole ? selectedRole.id : ""}
              onChange={handleRoleChange}
              required
            >
              <option value="">Select Role</option>
              {roles.map((role) => (
                <option key={role.id} value={role.id}>
                  {role.name}
                </option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-primary update-btn">
            <FontAwesomeIcon icon={faSave} className="icon-spacing" />
            Update Role
          </button>
        </form>
        {message && (
          <div className="mt-3 alert alert-success">
            <FontAwesomeIcon icon={faCheckCircle} className="icon-spacing" />
            {message}
          </div>
        )}
        {error && (
          <div className="mt-3 alert alert-danger">
            <FontAwesomeIcon icon={faExclamationTriangle} className="icon-spacing" />
            {error}
          </div>
        )}
      </div>
    </div>
  );
};

export default UpdateRole;