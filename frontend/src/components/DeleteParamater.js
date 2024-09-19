import React, { useState } from "react";
import UserService from "../services/user.service";

const DeleteParameter = () => {
  const [paramId, setParamId] = useState('');
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await UserService.delparam(paramId);
      setSuccess(true);
      setError(null);
    } catch (error) {
      console.error('Error deleting parameter:', error);
      if (error.response && error.response.status === 404) {
        setError('Parameter not found.');
      } else {
        setError('Error deleting parameter');
      }
    }
  };    

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card p-4 rounded">
            <h2 className="mt-4">Delete Parameter</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="paramId" className="form-label">Parameter ID:</label>
                <input type="number" id="paramId" className="form-control" value={paramId} onChange={(e) => setParamId(e.target.value)} required />
              </div>
              <button type="submit" className="btn btn-danger">Delete Parameter</button>
            </form>
            {success && (
              <div className="mt-4">
                <p className="text-success">Parameter deleted successfully.</p>
              </div>
            )}
            {error && <p className="mt-4 text-danger">Error: {error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteParameter;
