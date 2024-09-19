package com.prodapt.cmsprojectmain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.FeatureNotFoundException;
import com.prodapt.cmsprojectmain.exceptions.ParameterNotFoundException;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.exceptions.UserNotFoundException;
import com.prodapt.cmsprojectmain.service.FeatureService;
import com.prodapt.cmsprojectmain.service.ParameterService;
import com.prodapt.cmsprojectmain.service.ProductService;
import com.prodapt.cmsprojectmain.service.RoleService;
import com.prodapt.cmsprojectmain.service.UserEntityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "InternetService Admin API")
public class AdminController {

	private static final Logger loggers = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private UserEntityService userservice;

	@Autowired
	private RoleService roleservice;
	
	@Autowired
	private FeatureService featureservice;

	@Operation(summary = "Create Product in APP")
	@PostMapping("/addproduct")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		loggers.info("Inside addProduct " + AdminController.class.getName());
		Product prd = productService.createproduct(product);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<Product>(prd, HttpStatus.CREATED);
	}

	@Operation(summary = "Create Feature in APP")
	@PostMapping("/addfeature")
	public ResponseEntity<Features> addFeature(@RequestBody Features feature) {
		loggers.info("Inside addFeatures " + AdminController.class.getName());
		Features features = featureService.createFeature(feature);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<Features>(features, HttpStatus.CREATED);
	}

	@Operation(summary = "Get Products By id")
	@GetMapping("/getproductsbyId")
	public ResponseEntity<Product> getProductById(@RequestParam("id") Long productId) throws ProductNotFoundException {
		loggers.info("Inside getProductById " + AdminController.class.getName());
		Product product = productService.getProductById(productId);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}
	
	
	
	
	 @Operation(summary = "Get Features by Product Id")
	    @GetMapping("/getfeaturesbyproductidadmin")
	    public ResponseEntity<List<Features>> getFeaturesByProductId(@RequestParam("productId") Long productId) {
	        loggers.info("Inside getFeaturesByProductId " + ManagerController.class.getName());
	        List<Features> features = featureservice.getFeaturesByProductId(productId);
	        loggers.info("Call to service layer method is successful");
	        return new ResponseEntity<>(features, HttpStatus.OK);
	    }
	

	@Operation(summary = "Get Products By name")
	@GetMapping("/getproductsbyName")
	public ResponseEntity<Product> getProductByName(@RequestParam("name") String name) throws ProductNotFoundException {
		loggers.info("Inside getProductByName " + AdminController.class.getName());
		Product product = productService.getProductByName(name);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

	@Operation(summary = "Get All Products")
	@GetMapping("/getallproducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		loggers.info("Inside getAllProducts " + AdminController.class.getName());
		List<Product> products = productService.getAllProducts();
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@Operation(summary = "Update Product in APP")
	@PutMapping("/updateproduct")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product)
			throws ProductNotFoundException {
		loggers.info("Inside updateProduct " + AdminController.class.getName());
		Product updatedProduct = productService.updateProduct(product.getId(), product);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);

	}

	@Operation(summary = "Delete Product By Id")
	@PostMapping("/deleteproductbyid")
	public ResponseEntity<String> deleteProductById(@RequestBody Long productId) throws ProductNotFoundException {
		loggers.info("Inside DeleteProductById " + AdminController.class.getName());
		String deleteproduct = productService.deleteProductid(productId);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<String>(deleteproduct, HttpStatus.OK);

	}

	@Operation(summary = "Delete Feature By Id")
	@PostMapping("/deletefeaturebyid")
	public ResponseEntity<String> deleteFeatureById(@RequestBody Long featureId) throws FeatureNotFoundException {
		loggers.info("Inside DeleteFeatureById " + AdminController.class.getName());
		String deletefeature = featureService.deleteFeatureById(featureId);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<String>(deletefeature, HttpStatus.OK);

	}

	@Operation(summary = "Delete Parameter By Id")
	@PostMapping("/deleteparameterbyid")
	public ResponseEntity<String> deleteParameterById(@RequestBody Long parameterId) throws ParameterNotFoundException {
		loggers.info("Inside DeleteParameterById " + AdminController.class.getName());
		String deleteparameter = parameterService.deleteParameterById(parameterId);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<String>(deleteparameter, HttpStatus.OK);

	}

	@Autowired
	private UserEntityService userService;

	@PutMapping("/users/updaterole")
	public ResponseEntity<String> updateUserRole(@RequestParam Integer userId, @RequestBody Role role)
			throws UserNotFoundException, Exception {

		String result = userService.updateRole(userId, role);
		return ResponseEntity.ok(result);

	}

	@GetMapping("/getallusers")
	public ResponseEntity<Iterable<UserEntity>> getAllUsers() {
		Iterable<UserEntity> users = userservice.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/getallroles")
	public ResponseEntity<Iterable<Role>> getAllRoles() {
		Iterable<Role> role = roleservice.getAllRole();
		return ResponseEntity.ok(role);
	}
	
	@GetMapping("/getuserbyid")
	 public ResponseEntity<UserEntity> getUserById(@RequestParam("id") Integer id) {
        try {
            UserEntity user = userService.getUserEntityById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}