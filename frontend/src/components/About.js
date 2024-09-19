import React from "react";

const About = () => {
  return (
    <div className="container py-5 my-5">
      <div className="row align-items-center">
        <div className="col-md-6 order-md-2 d-flex justify-content-center mb-4 mb-md-0">
          <div className="about-image rounded-circle overflow-hidden">
            <img
              src="/R.png"
              height="400px"
              width="400px"
              alt="About CMS"
              className="img-fluid"
            />
          </div>
        </div>
        <div className="col-md-6 order-md-1">
          <div className="about-content bg-white p-4 rounded shadow-sm">
            <h1 className="text-primary fw-bold mb-4">About Us</h1>
            <p className="lead mb-4">
              Welcome to CMS (Catalogue Management System), your trusted partner
              in revolutionizing how businesses manage and showcase their
              internet service products. At CMS, we understand the challenges
              businesses face in organizing and optimizing their product
              catalogues. Our mission is to simplify this process through
              innovative technology and user-centric solutions.
            </p>
            <p className="mb-4">
              With a focus on efficiency and ease of use, CMS offers a
              comprehensive suite of features designed to enhance your
              catalogue management experience. Whether you're dealing with
              wireline or wireless products, our platform provides intuitive
              tools for seamless organization, visualization of product
              hierarchies, advanced search capabilities, role-based access
              control, and robust data import and integration functionalities.
            </p>
            <p>
              Our dedicated team is committed to delivering excellence,
              ensuring that your business maintains data accuracy, security,
              and integrity while optimizing operational efficiency. Partner
              with CMS today and discover a smarter way to manage and showcase
              your internet service products.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;
