package com.prodapt.cmsprojectmain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import com.prodapt.cmsprojectmain.dto.QuotationDTO;
import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.entities.Quotation;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.FeatureNotFoundException;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.exceptions.QuotationNotFoundException;
import com.prodapt.cmsprojectmain.service.FeatureService;
import com.prodapt.cmsprojectmain.service.ProductService;
import com.prodapt.cmsprojectmain.service.QuotationService;
import com.prodapt.cmsprojectmain.service.UserEntityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
 
@RestController
@RequestMapping("/api/v1/manager")
@Tag(name = "InternetService Manager API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ManagerController {
	 private static final Logger loggers = LoggerFactory.getLogger(ManagerController.class);

	    
	@Autowired
	private ProductService productService;
 
	@Autowired
	private QuotationService quotationService;
 
	@Autowired
	private ModelMapper modelMapper;
 
	@Autowired
	private UserEntityService userService;
	
	@Autowired
	private FeatureService featureservice;
 
	@Operation(summary = "Get Product By Name")
	@GetMapping("/getproductbyname")
	public ResponseEntity<Product> getProductByName(@RequestParam("name") String name) throws ProductNotFoundException {
		 loggers.info("Inside getProductByName " + ManagerController.class.getName());
		Product product = productService.getProductByName(name);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<>(product, HttpStatus.OK);
 
	}
 
	@Operation(summary = "Get Product By ID")
	@GetMapping("/getproductbyid")
	public ResponseEntity<Product> getProductById(@RequestParam("id") Long productId) throws ProductNotFoundException {
		loggers.info("Inside getProductById " + ManagerController.class.getName());
		Product product = productService.getProductById(productId);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<>(product, HttpStatus.OK);
 
	}
	
	
	@Operation(summary = "Get Feature by Id")
	@GetMapping("/getfeaturebyid")
	public ResponseEntity<Features> getFeatureById(@RequestParam("id") Long featureId) throws FeatureNotFoundException {
		loggers.info("Inside getFeatureById " + ManagerController.class.getName());
		Features feature=featureservice.getFeatureById(featureId);
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<>(feature, HttpStatus.OK);
 
	}
	
	
	 @Operation(summary = "Get Features by Product Id")
	    @GetMapping("/getfeaturesbyproductid")
	    public ResponseEntity<List<Features>> getFeaturesByProductId(@RequestParam("productId") Long productId) {
	        loggers.info("Inside getFeaturesByProductId " + ManagerController.class.getName());
	        List<Features> features = featureservice.getFeaturesByProductId(productId);
	        loggers.info("Call to service layer method is successful");
	        return new ResponseEntity<>(features, HttpStatus.OK);
	    }
	
 
	@Operation(summary = "Get All Products")
	@GetMapping("/getallproducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		loggers.info("Inside getAllProducts " + ManagerController.class.getName());
		List<Product> products = productService.getAllProducts();
		loggers.info("Call to service layer method is success");
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
 
	@Operation(summary = "Add Quotation")
	@PostMapping("/addquotation")
	public ResponseEntity<QuotationDTO> addQuotation(@RequestBody QuotationDTO quotationDTO)
	        throws  ProductNotFoundException, FeatureNotFoundException {
	    loggers.info("Inside addQuotation " + ManagerController.class.getName());

	    // Map QuotationDTO to Quotation entity
	    Quotation quotation = modelMapper.map(quotationDTO, Quotation.class);

	    // Manually setting user and product to avoid LazyInitializationException
	    Product product = productService.getProductById(quotationDTO.getProductId());
	    if (product == null) {
	        throw new ProductNotFoundException("Product not found");
	    }
	    Features feature = featureservice.getFeatureById(quotationDTO.getFeatureId());
	    if (feature == null) {
	        throw new FeatureNotFoundException("Feature not found");
	    }

	   
	    quotation.setProduct(product);
	    quotation.setFeature(feature);

	    // Save the quotation
	    Quotation savedQuotation = quotationService.addQuotation(quotation);

	    // Map saved Quotation entity back to QuotationDTO
	    QuotationDTO responseQuotationDTO = modelMapper.map(savedQuotation, QuotationDTO.class);

	    return new ResponseEntity<>(responseQuotationDTO, HttpStatus.CREATED);
	}

	@Operation(summary = "Get Quotation By ID")
	@GetMapping("/getquotationbyid")
	public ResponseEntity<QuotationDTO> getQuotationById(@RequestParam("id") Long quotationId)
			throws QuotationNotFoundException {
		loggers.info("Inside getQuotationById " + ManagerController.class.getName());
		// Fetch the quotation
		Quotation quotation = quotationService.getQuotationById(quotationId);
 
		// Map Quotation entity to QuotationDTO
		QuotationDTO responseQuotationDTO = modelMapper.map(quotation, QuotationDTO.class);
 
		return new ResponseEntity<>(responseQuotationDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getallusersmgr")
	public ResponseEntity<Iterable<UserEntity>> getAllUsers() {
		Iterable<UserEntity> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	 @GetMapping("/getallquotations")
	    public ResponseEntity<List<QuotationDTO>> getAllQuotations() {
	        loggers.info("Inside getAllQuotations " + ManagerController.class.getName());

	        // Fetch all quotations from the service
	        List<Quotation> quotations = quotationService.getAllQuotations();

	        // Map Quotation entities to QuotationDTOs using ModelMapper and stream API
	        List<QuotationDTO> responseQuotationDTOs = quotations.stream()
	                .map(quotation -> modelMapper.map(quotation, QuotationDTO.class))
	                .collect(Collectors.toList());

	        return new ResponseEntity<>(responseQuotationDTOs, HttpStatus.OK);
	    }
	 
	 
	 @Operation(summary = "Delete Quotation By Id")
		@PostMapping("/deletequotationbyid")
		public ResponseEntity<String> deleteQuotationById(@RequestBody Long productId) throws QuotationNotFoundException {
			loggers.info("Inside DeleteQuotationById " + ManagerController.class.getName());
			String deleteQuotation = quotationService.deleteQuotionbyid(productId);
			loggers.info("Call to service layer method is success");
			return new ResponseEntity<String>(deleteQuotation, HttpStatus.OK);

		}
	 
	 @Operation(summary = "Update Quotation")
	 @PutMapping("/updatequotation")
	 public ResponseEntity<QuotationDTO> updateQuotation(@RequestBody QuotationDTO quotationDTO)
	         throws QuotationNotFoundException {
	     loggers.info("Inside updateQuotation " + ManagerController.class.getName());

	     // Retrieve existing quotation
	     Quotation existingQuotation = quotationService.getQuotationById(quotationDTO.getId());

	     // Check if quotation exists
	     if (existingQuotation == null) {
	         throw new QuotationNotFoundException("Quotation not found with id: " + quotationDTO.getId());
	     }

	     // Update fields that can be modified
	     existingQuotation.setTotalAmount(quotationDTO.getTotalAmount());
	     existingQuotation.setQuantity(quotationDTO.getQuantity());

	     // Save updated quotation
	     Quotation updatedQuotation = quotationService.updateQuotation(existingQuotation);

	     // Map updated Quotation entity back to QuotationDTO
	     QuotationDTO responseQuotationDTO = modelMapper.map(updatedQuotation, QuotationDTO.class);

	     return new ResponseEntity<>(responseQuotationDTO, HttpStatus.OK);
	 }
	 
	 
}