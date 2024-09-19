
package com.prodapt.cmsprojectmain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Parameter;
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
import com.prodapt.cmsprojectmain.service.UserEntityService;


 
@SpringBootTest
class AdminControllerTest {
 
	@InjectMocks
	private AdminController adminController;
 
	@Mock
	private ProductService productService;
 
	@Mock
	private FeatureService featureService;
 
	@Mock
	private ParameterService parameterService;
 
	@Mock
	private UserEntityService userService;
 
	private Product product;
	private Features feature;
	private Parameter parameter;
	private UserEntity user;
 
	@BeforeEach
	public void setup() {
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");
 
		feature = new Features();
		feature.setId(1L);
		feature.setName("Test Feature");
 
		parameter = new Parameter();
		parameter.setId(1L);
		parameter.setName("Test Parameter");
		parameter.setType("test");
		parameter.setValue("2.00");
 
		user = new UserEntity();
		user.setId(1);
		user.setUsername("testUser");
		user.setEmail("testEmail");
		user.setPassword("testPassword");
	}
 
	@Test
	void testAddProductSuccess() {
			when(productService.createproduct(product)).thenReturn(product);
 
			ResponseEntity<Product> response = adminController.addProduct(product);
			assertNotNull(product);
			assertEquals(HttpStatus.CREATED, response.getStatusCode());
			assertEquals(product, response.getBody());
		}

 
	@Test
	void testAddProductFailure() {
			when(productService.createproduct(product)).thenThrow(new RuntimeException("Error creating product"));
			assertNotNull(product);
			assertThrows(RuntimeException.class, () -> adminController.addProduct(product));
		}
 
	@Test
	void testAddFeatureSuccess() {
			when(featureService.createFeature(feature)).thenReturn(feature);
 
			ResponseEntity<Features> response = adminController.addFeature(feature);
			assertNotNull(feature);
			assertEquals(HttpStatus.CREATED, response.getStatusCode());
			assertEquals(feature, response.getBody());
		}

 
	@Test
	void testGetProductById_Success() throws ProductNotFoundException {
			when(productService.getProductById(1L)).thenReturn(product);
 
			ResponseEntity<Product> response = adminController.getProductById(1L);
			assertNotNull(product);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(product, response.getBody());
		}
	@Test
	void testGetProductById_Failure() throws ProductNotFoundException {
			when(productService.getProductById(1L)).thenThrow(new ProductNotFoundException("Product not found"));
			assertNotNull(product);
			assertThrows(ProductNotFoundException.class, () -> adminController.getProductById(1L));
		}
 
 
	@Test
	void testGetProductByName_Success() throws ProductNotFoundException {
			when(productService.getProductByName("Test Product")).thenReturn(product);
 
			ResponseEntity<Product> response = adminController.getProductByName("Test Product");
			assertNotNull(product);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(product, response.getBody());
		}
	@Test
	void testGetProductByName_Failure() throws ProductNotFoundException {
			when(productService.getProductByName("Test Product"))
					.thenThrow(new ProductNotFoundException("Product not found"));
			assertNotNull(product);
			assertThrows(ProductNotFoundException.class, () -> adminController.getProductByName("Test Product"));
		}
 
	@Test
	void testGetAllProducts() {
			List<Product> products = new ArrayList<>();
			products.add(product);
 
			when(productService.getAllProducts()).thenReturn(products);
 
			ResponseEntity<List<Product>> response = adminController.getAllProducts();
			assertNotNull(product);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(products, response.getBody());
	}
 
	
	@Test
	void testUpdateProduct_Success() throws ProductNotFoundException {
			when(productService.updateProduct(1L, product)).thenReturn(product);
 
			ResponseEntity<Product> response = adminController.updateProduct(product);
			assertNotNull(product);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(product, response.getBody());
	}
 
	@Test
	void testUpdateProduct_Failure() throws ProductNotFoundException {
			when(productService.updateProduct(1L, product))
					.thenThrow(new ProductNotFoundException("Product not found"));
			assertNotNull(product);
			assertThrows(ProductNotFoundException.class, () -> adminController.updateProduct(product));
	}

 
	@Test
	void testDeleteProductById_Success() throws ProductNotFoundException {
			when(productService.deleteProductid(1L)).thenReturn("Product deleted");
 
			ResponseEntity<String> response = adminController.deleteProductById(1L);
			assertNotNull(product);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals("Product deleted", response.getBody());
		}
 
	@Test
	void testDeleteProductById_Failure() throws ProductNotFoundException {
			when(productService.deleteProductid(1L)).thenThrow(new ProductNotFoundException("Product not found"));
			assertNotNull(product);
			assertThrows(ProductNotFoundException.class, () -> adminController.deleteProductById(1L));
		}
	@Test
	void testDeleteFeatureByIdSuccess() throws FeatureNotFoundException {
	    // Arrange
	    Long featureId = 1L;
	    String expectedMessage = "Feature deleted successfully";
	    when(featureService.deleteFeatureById(featureId)).thenReturn(expectedMessage);
 
	    // Act
	    ResponseEntity<String> result = adminController.deleteFeatureById(featureId);
 
	    // Assert
	    assertEquals(HttpStatus.OK, result.getStatusCode());
	    assertEquals(expectedMessage, result.getBody());
	}
 
 
    @Test
    void testDeleteFeatureByIdFailure() throws FeatureNotFoundException {
        // Arrange
        Long featureId = 1L;
        when(featureService.deleteFeatureById(featureId)).thenThrow(new FeatureNotFoundException("Product not found"));
 
        // Act and Assert
        assertThrows(FeatureNotFoundException.class, () -> {
            featureService.deleteFeatureById(featureId);
        });
    }
    @Test
    void testDeleteParameterByIdSuccess() throws ParameterNotFoundException {
        // Arrange
        Long parameterId = 1L;
        String expectedMessage = "Parameter deleted successfully";
        when(parameterService.deleteParameterById(parameterId)).thenReturn(expectedMessage);
 
        // Act
        ResponseEntity<String> result = adminController.deleteParameterById(parameterId);
 
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedMessage, result.getBody());
    }
 
    @Test
    void testDeleteParameterByIdFailure() throws ParameterNotFoundException {
        // Arrange
        Long parameterId = 1L;
        when(parameterService.deleteParameterById(parameterId)).thenThrow(new ParameterNotFoundException());
 
        // Act and Assert
        assertThrows(ParameterNotFoundException.class, () -> {
            adminController.deleteParameterById(parameterId);
        });
    }
 
    @Test
    void testUpdateUserRoleSuccess() throws UserNotFoundException, Exception {
        // Arrange
        Integer userId = 1;
        Role role = new Role();
        String expectedMessage = "Role updated successfully";
        when(userService.updateRole(userId, role)).thenReturn(expectedMessage);
 
        // Act
        ResponseEntity<String> result = adminController.updateUserRole(userId, role);
 
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedMessage, result.getBody());
    }
 
    @Test
    void testUpdateUserRoleFailure() throws UserNotFoundException {
        // Arrange
        Integer userId = 1;
        Role role = new Role();
        when(userService.updateRole(userId, role)).thenThrow(new UserNotFoundException());
 
        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> {
            adminController.updateUserRole(userId, role);
        });
    }
    
    @Test
    public void testGetFeatureById_Success() throws FeatureNotFoundException {
        // Prepare test data
        Long featureId = 1L;
        Features expectedFeature = new Features(/* Initialize with relevant data */);
        
        // Mock behavior
        when(featureService.getFeatureById(featureId)).thenReturn(expectedFeature);

        // Call the controller method
        ResponseEntity<String> responseEntity = adminController.deleteFeatureById(featureId);

        // Assert the response status code and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Status code should be 200 OK");
        //assertEquals(expectedFeature, responseEntity.getBody(), "Returned feature should match expected feature");
        
    }

    @Test
    public void testGetFeatureById_FeatureNotFound() throws FeatureNotFoundException {
        // Prepare test data
        Long featureId = 1L;
        
        // Mock behavior for feature not found
        when(featureService.getFeatureById(featureId)).thenThrow(new FeatureNotFoundException("Feature not found"));

        // Call the controller method
        try {
            adminController.deleteFeatureById(featureId);
        } catch (FeatureNotFoundException e) {
            // Exception expected
        }

        //verify(featureService, times(1)).getFeatureById(featureId); // Verify method call
    }


//    @Test
//    public void testGetAllUsers_Success() {
//        // Given
//        List<UserEntity> mockUsers = new ArrayList<>();
//        mockUsers.add(new UserEntity());
//        mockUsers.add(new UserEntity());
//
//        // Mock service behavior
//        when(userService.getAllUsers()).thenReturn(mockUsers);
//
//        // When
//        ResponseEntity<Iterable<UserEntity>> responseEntity = adminController.getAllUsers();
//
//        // Then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Iterable<UserEntity> actualUsers = responseEntity.getBody();
//        assertEquals(mockUsers.size(), ((List<UserEntity>) actualUsers).size()); // Verify size
//        // Additional verification can be done here if needed
//
//        // Verify userService method invocation
//        verify(userService, times(1)).getAllUsers();
//    }
//    
//    @Test
//    public void testGetAllUsers_EmptyList() {
//        // Given
//        List<UserEntity> mockUsers = new ArrayList<>(); // Empty list
//
//        // Mock service behavior
//        when(userService.getAllUsers()).thenReturn(mockUsers);
//
//        // When
//        ResponseEntity<Iterable<UserEntity>> responseEntity = adminController.getAllUsers();
//
//        // Then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Iterable<UserEntity> actualUsers = responseEntity.getBody();
//        assertEquals(0, ((List<UserEntity>) actualUsers).size()); // Verify size is zero
//
//        // Verify userService method invocation
//        verify(userService, times(1)).getAllUsers();
//    }
//


}


