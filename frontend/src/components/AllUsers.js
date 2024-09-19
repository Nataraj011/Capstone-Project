import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import UserService from "../services/user.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faUsers, 
  faUser, 
  faEnvelope, 
  faUserTag, 
  faEdit 
} from "@fortawesome/free-solid-svg-icons";
import "./AllUsers.css";

const AllUsers = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await UserService.getalluser();
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  return (
    <div className="all-users-container">
      <h2 className="all-users-title">
        <FontAwesomeIcon icon={faUsers} className="icon-spacing" />
        User List
      </h2>
      <div className="table-responsive">
        <table className="table table-hover">
          <thead>
            <tr>
              <th>ID</th>
              <th>
                <FontAwesomeIcon icon={faUser} className="icon-spacing" />
                Username
              </th>
              <th>
                <FontAwesomeIcon icon={faEnvelope} className="icon-spacing" />
                Email
              </th>
              <th>
                <FontAwesomeIcon icon={faUserTag} className="icon-spacing" />
                Role
              </th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>{user.role.name}</td>
                <td>
                  <Link to={`/update-rolepage/${user.id}`} className="btn btn-primary update-role-btn">
                    <FontAwesomeIcon icon={faEdit} className="icon-spacing" />
                    Update Role
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AllUsers;