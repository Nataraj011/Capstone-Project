import React, { useState, useEffect } from "react";
import UserService from "../services/user.service";
import About from "./About";
import Footer from "./Footer";
import "./Allproducts.css"; // Ensure your CSS file is correctly imported
import Intro from "./Intro";

const Homepage = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <>
      <div className="container-home fade-in">
        <Intro></Intro>
        <hr></hr>
        <About className="slide-in" /> {/* Apply slide-in animation */}
       
      </div>

      <Footer />
    </>
  );
};

export default Homepage;
