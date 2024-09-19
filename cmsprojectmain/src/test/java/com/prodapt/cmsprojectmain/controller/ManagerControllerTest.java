package com.prodapt.cmsprojectmain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
 
@SpringBootTest
public class ManagerControllerTest {
 
	@InjectMocks
	private ManagerController managerController;
 
	@Mock
	private ProductService productService;
	
	@Mock
	private FeatureService featureService;
 
	@Mock
	private QuotationService quotationService;
 
	@Mock
	private UserEntityService userService;
 
	@Mock
	private ModelMapper modelMapper;
	private Product product;
	private UserEntity user;
	private Quotation quotation;
	private Features feature;
	
 
	@BeforeEach
	public void setup() {
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");
		
		feature = new Features();
		feature.setId(1L);
		feature.setName("Test Feature");
 
		user = new UserEntity();
		user.setId(1);
		user.setUsername("testuser");
		user.setEmail("testuser@example.com");
		user.setPassword("testpassword");
 
		quotation = new Quotation();
		quotation.setId(1L);
		
		quotation.setProduct(product);
		quotation.setTotalAmount(100.0);
		quotation.setQuantity(2);
 
		
	}
 
	@Test
	void testGetProductByName_Success() throws ProductNotFoundException {
		when(productService.getProductByName("Test Product")).thenReturn(product);
 
		ResponseEntity<Product> response = managerController.getProductByName("Test Product");
		assertNotNull(product);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody());
	}
 
	@Test
	void testGetProductByName_Failure() throws ProductNotFoundException {
		when(productService.getProductByName("Test Product"))
				.thenThrow(new ProductNotFoundException("Product not found"));
		assertNotNull(product);
		assertThrows(ProductNotFoundException.class, () -> managerController.getProductByName("Test Product"));
	}
 
	@Test
	void testGetProductById_Success() throws ProductNotFoundException {
		when(productService.getProductById(1L)).thenReturn(product);
 
		ResponseEntity<Product> response = managerController.getProductById(1L);
		assertNotNull(product);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody());
	}
 
	@Test
	void testGetProductById_Failure() throws ProductNotFoundException {
		when(productService.getProductById(1L)).thenThrow(new ProductNotFoundException("Product not found"));
		assertNotNull(product);
		assertThrows(ProductNotFoundException.class, () -> managerController.getProductById(1L));
	}
 
	@Test
	void testGetAllProducts() {
		List<Product> products = new ArrayList<>();
		products.add(product);
 
		when(productService.getAllProducts()).thenReturn(products);
 
		ResponseEntity<List<Product>> response = managerController.getAllProducts();
		assertNotNull(product);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(products, response.getBody());
	}
 
//	@Test
//	void testAddQuotationSuccess() throws UserNotFoundException, ProductNotFoundException {
//		// Arrange
//		quotationDTO = new QuotationDTO();
//		quotationDTO.setId(1L);
//		quotationDTO.setUserId(user.getId());
//		quotationDTO.setProductId(product.getId());
//		quotationDTO.setTotalAmount(100.0);
//		quotationDTO.setQuantity(2);
//		
//		when(userService.getUserEntityById(quotationDTO.getUserId())).thenReturn(user);
//		when(productService.getProductById(quotationDTO.getProductId())).thenReturn(product);
//		when(quotationService.addQuotation(quotation)).thenReturn(quotation);
// 
//		ResponseEntity<QuotationDTO> result = managerController.addQuotation(quotationDTO);
// 
//		// Assert
//		assertNotNull(quotationDTO);
//		assertNotNull(quotation);
//		assertEquals(HttpStatus.CREATED, result.getStatusCode());
//		assertEquals(quotationDTO, result.getBody());
//	}
 
	
 
	@Test
	void testGetQuotationByIdSuccess() throws QuotationNotFoundException {
		// Arrange
		Long quotationId = 1L;
		Quotation quotation = new Quotation();
		QuotationDTO quotationDTO = new QuotationDTO();
 
		when(quotationService.getQuotationById(quotationId)).thenReturn(quotation);
		when(modelMapper.map(quotation, QuotationDTO.class)).thenReturn(quotationDTO);
 
		// Act
		ResponseEntity<QuotationDTO> result = managerController.getQuotationById(quotationId);
 
		// Assert
		assertNotNull(quotation);
		assertNotNull(quotationDTO);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(quotationDTO, result.getBody());
	}
 
	@Test
	void testGetQuotationByIdFailure() throws QuotationNotFoundException {
		// Arrange
		Long quotationId = 1L;
		when(quotationService.getQuotationById(quotationId)).thenThrow(new QuotationNotFoundException());
 
		// Act and Assert
		assertNotNull(quotation);
		//assertNotNull(quotationDTO);
		assertThrows(QuotationNotFoundException.class, () -> {
			managerController.getQuotationById(quotationId);
		});
	}
	
	@Test
	public void testGetFeatureById_Success() throws FeatureNotFoundException {
	    // Assume featureservice is mocked or injected appropriately
	    
	    // Given
	    Long validFeatureId = 1L;
	    Features mockFeature = new Features(); // Create a mock feature object or use a builder pattern
	    
	    // Mock service behavior
	    when(featureService.getFeatureById(validFeatureId)).thenReturn(mockFeature);
	    
	    // When
	    ResponseEntity<Features> responseEntity = managerController.getFeatureById(validFeatureId);
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(mockFeature, responseEntity.getBody());
	    
	    // Optionally, verify loggers.info() calls if logging verification is necessary
	    // verify(loggers, times(2)).info(anyString());
	}
	
	@Test
	public void testGetFeatureById_NotFound() throws FeatureNotFoundException {
	    // Assume featureservice is mocked or injected appropriately
	    
	    // Given
	    Long invalidFeatureId = 999L; // Assuming this ID does not exist
	    // Mock service behavior to throw FeatureNotFoundException
	    when(featureService.getFeatureById(invalidFeatureId)).thenThrow(new FeatureNotFoundException());
	    
	    // When
	    try {
	        ResponseEntity<Features> responseEntity = managerController.getFeatureById(invalidFeatureId);
	        
	        // Fail if no exception is thrown
	        fail("FeatureNotFoundException should have been thrown.");
	        
	    } catch (FeatureNotFoundException e) {
	        // Expected behavior
	        // Optionally, assert or verify additional aspects of the exception
	        
	    }
	}
	
	@Test
	public void testGetFeaturesByProductId_Success() {
	    // Assume featureservice is mocked or injected appropriately
	    
	    // Given
	    Long validProductId = 1L;
	    List<Features> mockFeaturesList = Arrays.asList(new Features(), new Features()); // Create a mock list of features
	    
	    // Mock service behavior
	    when(featureService.getFeaturesByProductId(validProductId)).thenReturn(mockFeaturesList);
	    
	    // When
	    ResponseEntity<List<Features>> responseEntity = managerController.getFeaturesByProductId(validProductId);
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(mockFeaturesList, responseEntity.getBody());
	    
	    // Optionally, verify loggers.info() calls if logging verification is necessary
	    // verify(loggers, times(2)).info(anyString());
	}

	@Test
	public void testGetFeaturesByProductId_NoFeaturesFound() {
	    // Assume featureservice is mocked or injected appropriately
	    
	    // Given
	    Long invalidProductId = 999L; // Assuming this ID does not exist or has no associated features
	    List<Features> emptyFeaturesList = Collections.emptyList();
	    
	    // Mock service behavior to return an empty list
	    when(featureService.getFeaturesByProductId(invalidProductId)).thenReturn(emptyFeaturesList);
	    
	    // When
	    ResponseEntity<List<Features>> responseEntity = managerController.getFeaturesByProductId(invalidProductId);
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(emptyFeaturesList, responseEntity.getBody());
	    
	    // Optionally, verify loggers.info() calls if logging verification is necessary
	    // verify(loggers, times(2)).info(anyString());
	}

	@Test
    public void testAddQuotation_Success() throws ProductNotFoundException, FeatureNotFoundException {
        // Given
        QuotationDTO quotationDTO = new QuotationDTO();
        quotationDTO.setProductId(1L); // Existing product ID
        quotationDTO.setFeatureId(1L); // Existing feature ID

        Quotation mockQuotation = new Quotation();
        mockQuotation.setId(1L); // Assuming the quotation service returns a saved quotation with ID

        // Mock behavior of productService
        Product mockProduct = new Product();
        when(productService.getProductById(quotationDTO.getProductId())).thenReturn(mockProduct);

        // Mock behavior of featureService
        Features mockFeature = new Features();
        when(featureService.getFeatureById(quotationDTO.getFeatureId())).thenReturn(mockFeature);

        // Mock behavior of quotationService
        when(quotationService.addQuotation(any(Quotation.class))).thenReturn(mockQuotation);

        // Mock behavior of modelMapper
        when(modelMapper.map(any(), eq(Quotation.class))).thenReturn(new Quotation());
        when(modelMapper.map(any(), eq(QuotationDTO.class))).thenReturn(new QuotationDTO());

        // When
        ResponseEntity<QuotationDTO> responseEntity = managerController.addQuotation(quotationDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        

        // Verify productService method invocation
        verify(productService, times(1)).getProductById(quotationDTO.getProductId());

        // Verify featureService method invocation
        verify(featureService, times(1)).getFeatureById(quotationDTO.getFeatureId());

        // Verify quotationService method invocation
        verify(quotationService, times(1)).addQuotation(any(Quotation.class));
    }
	@Test
	public void testAddQuotation_ProductNotFound() throws ProductNotFoundException, FeatureNotFoundException {
	    // Assume productService, featureservice, and quotationService are mocked or injected appropriately
	    
	    // Given
	    QuotationDTO quotationDTO = new QuotationDTO();
	    quotationDTO.setProductId(999L); // Non-existent product ID
	    quotationDTO.setFeatureId(1L); // Existing feature ID
	    
	    // Mock service behaviors
	    when(productService.getProductById(quotationDTO.getProductId())).thenReturn(null); // Product service returns null
	    
	    // When
	    try {
	        managerController.addQuotation(quotationDTO);
	        fail("ProductNotFoundException should have been thrown.");
	    } catch (ProductNotFoundException e) {
	        // Expected behavior
	        // Optionally, assert or verify additional aspects of the exception
	    }
	    
	    // Optionally, verify loggers.info() calls if logging verification is necessary
	    // verify(loggers, times(1)).info(anyString());
	}
	
	@Test
	public void testAddQuotation_FeatureNotFound() throws ProductNotFoundException, FeatureNotFoundException {
	    // Assume productService, featureservice, and quotationService are mocked or injected appropriately
	    
	    // Given
	    QuotationDTO quotationDTO = new QuotationDTO();
	    quotationDTO.setProductId(1L); // Existing product ID
	    quotationDTO.setFeatureId(999L); // Non-existent feature ID
	    
	    // Mock service behaviors
	    when(productService.getProductById(quotationDTO.getProductId())).thenReturn(new Product()); // Mock a product object
	    when(featureService.getFeatureById(quotationDTO.getFeatureId())).thenReturn(null); // Feature service returns null
	    
	    // When
	    try {
	        managerController.addQuotation(quotationDTO);
	        fail("FeatureNotFoundException should have been thrown.");
	    } catch (FeatureNotFoundException e) {
	        // Expected behavior
	        // Optionally, assert or verify additional aspects of the exception
	    }
	    
	    // Optionally, verify loggers.info() calls if logging verification is necessary
	    // verify(loggers, times(1)).info(anyString());
	}


	@Test
	public void testGetAllUsers_Success() {
	    // Assume userService is mocked or injected appropriately
	    
	    // Given
	    List<UserEntity> mockUsers = Arrays.asList(
	        new UserEntity(),
	        new UserEntity()
	    );
	    
	    // Mock service behavior
	    when(userService.getAllUsers()).thenReturn(mockUsers);
	    
	    // When
	    ResponseEntity<Iterable<UserEntity>> responseEntity = managerController.getAllUsers();
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertNotNull(responseEntity.getBody());
	    assertEquals(mockUsers.size(), ((Collection<UserEntity>) responseEntity.getBody()).size());
	    // Optionally, assert more specific details if needed
	    
	    // Verify userService method invocation
	    verify(userService, times(1)).getAllUsers();
	}

	@Test
	public void testGetAllUsers_EmptyResult() {
	    // Assume userService is mocked or injected appropriately
	    
	    // Given
	    List<UserEntity> emptyList = Collections.emptyList();
	    
	    // Mock service behavior
	    when(userService.getAllUsers()).thenReturn(emptyList);
	    
	    // When
	    ResponseEntity<Iterable<UserEntity>> responseEntity = managerController.getAllUsers();
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertNotNull(responseEntity.getBody());
	    assertEquals(0, ((Collection<UserEntity>) responseEntity.getBody()).size());
	    // Optionally, assert more specific details if needed
	    
	    // Verify userService method invocation
	    verify(userService, times(1)).getAllUsers();
	}

	@Test
	public void testGetAllQuotations_Success() {
	    // Assume quotationService and modelMapper are mocked or injected appropriately
	    
	    // Given
	    List<Quotation> mockQuotations = Arrays.asList(
	        new Quotation(),
	        new Quotation()
	    );
	    
	    // Mock service behavior
	    when(quotationService.getAllQuotations()).thenReturn(mockQuotations);
	    when(modelMapper.map(any(Quotation.class), eq(QuotationDTO.class)))
	        .thenAnswer(invocation -> {
	            Quotation quotation = invocation.getArgument(0);
	            return new QuotationDTO();
	        });
	    
	    // When
	    ResponseEntity<List<QuotationDTO>> responseEntity = managerController.getAllQuotations();
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertNotNull(responseEntity.getBody());
	    assertEquals(mockQuotations.size(), responseEntity.getBody().size());
	    // Optionally, assert more specific details if needed
	    
	    // Verify quotationService method invocation
	    verify(quotationService, times(1)).getAllQuotations();
	    
	    // Verify modelMapper method invocation
	    verify(modelMapper, times(mockQuotations.size())).map(any(Quotation.class), eq(QuotationDTO.class));
	}
	
	@Test
	public void testGetAllQuotations_EmptyResult() {
	    // Assume quotationService is mocked or injected appropriately
	    
	    // Given
	    List<Quotation> emptyList = Collections.emptyList();
	    
	    // Mock service behavior
	    when(quotationService.getAllQuotations()).thenReturn(emptyList);
	    
	    // When
	    ResponseEntity<List<QuotationDTO>> responseEntity = managerController.getAllQuotations();
	    
	    // Then
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertNotNull(responseEntity.getBody());
	    assertEquals(0, responseEntity.getBody().size());
	    // Optionally, assert more specific details if needed
	    
	    // Verify quotationService method invocation
	    verify(quotationService, times(1)).getAllQuotations();
	}
	
	  @Test
	    public void testDeleteQuotationById_Success() throws QuotationNotFoundException {
	        // Given
	        Long quotationId = 1L;
	        String successMessage = "Quotation deleted successfully";
	        
	        // Mock service behavior
	        when(quotationService.deleteQuotionbyid(quotationId)).thenReturn(successMessage);
	        
	        // When
	        ResponseEntity<String> responseEntity = managerController.deleteQuotationById(quotationId);
	        
	        // Then
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(successMessage, responseEntity.getBody());
	        
	        // Verify quotationService method invocation
	        verify(quotationService, times(1)).deleteQuotionbyid(quotationId);
	    }
//	  @Test
//	    public void testDeleteQuotationById_QuotationNotFound() throws QuotationNotFoundException {
//	        // Given
//	        Long quotationId = 999L; // Non-existent quotation ID
//	        
//	        // Mock service behavior to throw QuotationNotFoundException
//	        when(quotationService.deleteQuotionbyid(eq(quotationId))).thenThrow(new QuotationNotFoundException("Quotation not found"));
//	        
//	        // When
//	        ResponseEntity<String> responseEntity = managerController.deleteQuotationById(quotationId);
//	        
//	        // Then
//	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//	        assertEquals("Quotation not found", responseEntity.getBody());
//	        
//	        // Verify quotationService method invocation
//	        verify(quotationService, times(1)).deleteQuotionbyid(eq(quotationId));
//	    }
//	  
//	  @Test
//	    public void testDeleteQuotationById_InternalServerError() throws QuotationNotFoundException {
//	        // Given
//	        Long quotationId = 1L;
//
//	        // Mock service behavior to throw QuotationNotFoundException
//	        when(quotationService.deleteQuotionbyid(eq(quotationId))).thenThrow(new QuotationNotFoundException("Quotation not found"));
//
//	        // When
//	        ResponseEntity<String> responseEntity = managerController.deleteQuotationById(quotationId);
//
//	        // Then
//	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//	        assertEquals("Quotation not found", responseEntity.getBody());
//
//	        // Verify quotationService method invocation
//	        verify(quotationService, times(1)).deleteQuotionbyid(eq(quotationId));
//	    }
}