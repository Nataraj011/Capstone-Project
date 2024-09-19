package com.prodapt.cmsprojectmain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.service.ProductService;
import com.prodapt.cmsprojectmain.utility.QUERYMAPPER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/customer")
@Tag(name = "InternetService Customer API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

	private static final Logger loggers = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	ProductService productService;

	@Operation(summary = "Get Product By Name")
	@GetMapping("/getproductbyname")
	public ResponseEntity<Product> getProductByName(@RequestParam("name") String name) throws ProductNotFoundException {
		loggers.info("Inside getProductByName " + CustomerController.class.getName());
		Product product = productService.getProductByName(name);
		loggers.info("Call to service layer method is success");
		loggers.info(QUERYMAPPER.RECORD_EXITS);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@Operation(summary = "Get All Products")
	@GetMapping("/getallproducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		loggers.info("Inside getAllProducts " + CustomerController.class.getName());
		List<Product> products = productService.getAllProducts();
		loggers.info("Call to service layer method is success");
		loggers.info(QUERYMAPPER.RECORD_EXITS);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
}
