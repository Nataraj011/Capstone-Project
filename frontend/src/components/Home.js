import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const Home = () => {
  const [expandedFeatures, setExpandedFeatures] = useState({});
  const [showMore, setShowMore] = useState(false);
 
  const toggleFeature = (feature) => {
    setExpandedFeatures(prevState => ({
      ...prevState,
      [feature]: !prevState[feature]
    }));
  };
 
  const features = [
    {
      title: "Catalogue Management",
      description: "Implement a comprehensive catalogue management system to efficiently manage and display a wide range of internet service products, covering both wireline and wireless options."
    },
    {
      title: "Product Hierarchy Visualization",
      description: "Develop a user-friendly interface to visualize the product-feature-parameter hierarchy, enabling easy navigation and understanding of complex product relationships."
    },
    {
      title: "Search and Filtering",
      description: "Design a robust search functionality with advanced filtering options to quickly locate specific products, features, or parameters within the catalogue."
    },
    {
      title: "Role-Based Access Control",
      description: "Implement a role-based access control system to ensure that administrators and managers have appropriate permissions to manage and view product information, while maintaining data security and integrity."
    },
    {
      title: "Data Import and Integration",
      description: "Develop a seamless data import and integration process to populate the catalogue with product information from various sources, including JSON files and Mockaroo API, ensuring data accuracy and consistency."
    }
  ];

  return (
    <div className="container mt-5">
      <div className="border border-primary p-3 mb-3 rounded">
        <header className="jumbotron text-center py-5" style={{ backgroundImage: 'linear-gradient(180deg, #87ceeb, #00bfff)', color: 'white' }}>
          <h1 className="display-4 font-weight-light">Catalogue Management System for Internet Service Products</h1>
          <p className="lead">Organize, Optimize, and Thrive: Catalogue Management Made Easy</p>
        </header>
      </div>

      {/* Features Section */}
      <section className="mb-5">
        <div className="row text-center">
          {features.slice(0, 2).map((feature, index) => (
            <div key={index} className="col-md-6 col-sm-12 mb-4">
              <div className={`card border-primary h-100 ${expandedFeatures[feature.title] ? 'bg-light' : ''}`}>
                <button
                  className="btn btn-link card-body text-primary text-decoration-none"
                  onClick={() => toggleFeature(feature.title)}
                >
                  <div className="card-content">
                    {expandedFeatures[feature.title] ? (
                      <p className="card-text">{feature.description}</p>
                    ) : (
                      <h5 className="card-title">{feature.title}</h5>
                    )}
                  </div>
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* More Features Button */}
      <div className="text-center mb-5">
        {!showMore && (
          <button className="btn btn-primary" onClick={() => setShowMore(!showMore)}>
            Explore Additional Features
          </button>
        )}
      </div>

      {/* Additional Features Section */}
      {showMore && (
        <section className="mb-5">
          <div className="row text-center">
            {features.slice(2).map((feature, index) => (
              <div key={index} className="col-md-4 col-sm-12 mb-4">
                <div className={`card border-primary h-100 ${expandedFeatures[feature.title] ? 'bg-light' : ''}`}>
                  <button
                    className="btn btn-link card-body text-primary text-decoration-none"
                    onClick={() => toggleFeature(feature.title)}
                  >
                    <div className="card-content">
                      {expandedFeatures[feature.title] ? (
                        <p className="card-text">{feature.description}</p>
                      ) : (
                        <h5 className="card-title">{feature.title}</h5>
                      )}
                    </div>
                  </button>
                </div>
              </div>
            ))}
          </div>
          <div className="text-center mt-4">
            <button className="btn btn-primary" onClick={() => setShowMore(!showMore)}>
              Hide Additional Features
            </button>
          </div>
        </section>
      )}
    </div>
  );
};

export default Home;
