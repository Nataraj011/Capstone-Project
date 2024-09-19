import React from "react";

const Intro = () => {
  return (
    <div className="container py-5 my-5">
      <div className="intro-box bg-white rounded shadow p-5">
        <div className="row">
          <div className="col-md-6">
            <div className="intro-image">
              <img
                src="/images.jpg"
                alt="Intro"
                className="img-fluid rounded"
                style={{ maxWidth: "100%", height: "auto" }}
              />
            </div>
          </div>
          <div className="col-md-6 d-flex align-items-center">
            <div className="intro-content">
              <h1 className="text-primary fw-bold mb-4">
                Catalogue Management System for Internet Service Products
              </h1>
              <p className="lead mb-4">
                Welcome to the Catalogue Management System (CMS), your premier
                platform for efficient management and display of internet service
                products. Designed to meet the diverse needs of telecom giants
                like AT&T, Verizon, and T-Mobile, our CMS provides a robust
                solution for administrators and managers alike.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Intro;
