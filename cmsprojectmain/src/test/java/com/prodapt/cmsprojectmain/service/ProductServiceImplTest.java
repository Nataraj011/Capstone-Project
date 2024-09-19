package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.repositories.ProductRepository;
import com.prodapt.cmsprojectmain.utility.QUERYMAPPER;
 
@SpringBootTest
public class ProductServiceImplTest {
//	private static final Logger loggers = LoggerFactory.getLogger(ParameterServiceImpl.class);
 
	@Mock
	private ProductRepository repo;
 
	@InjectMocks
	private ProductServiceImpl productService;
 
	private Product product;
 
	@BeforeEach
	public void setup() {
 
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");
 
	}
 
	@Test
	void testCreateProduct() {
 
		when(repo.save(product)).thenReturn(product);
 
		Product result = productService.createproduct(product);
 
		assertNotNull(product);
		assertEquals(product, result);
	}
 
	@Test
	void testGetProductById() throws ProductNotFoundException {
 
		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.of(product));
 
		// Act
		Product result = productService.getProductById(1L);
 
		// Assert
		assertNotNull(product);
		assertEquals(product, result);
 
	}
 
	@Test
	public void testGetProductByIdNotFound() {
		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.empty());
 
		// Act and Assert
		assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
	}
 
	@Test
	public void testGetProductByName() throws ProductNotFoundException {
 
		// Arrange
		when(repo.findByName("Test Product")).thenReturn(Optional.of(product));
 
		// Act
		Product result = productService.getProductByName("Test Product");
 
		// Assert
		assertNotNull(product);
		assertEquals(product, result);
 
	}
 
	@Test
	public void testGetProductByNameNotFound() throws ProductNotFoundException {
		if (productService != null) {
			// Arrange
			when(repo.findByName("NoProduct")).thenReturn(Optional.empty());
 
			// Act
			assertThrows(ProductNotFoundException.class, () -> productService.getProductByName("NoProduct"));
		}
	}
 
	@Test
	public void testGetAllProducts() {
 
		// Arrange
		List<Product> products = new ArrayList<>();
		products.add(product);
 
		when(repo.findAll()).thenReturn(products);
 
		// Act
		List<Product> result = productService.getAllProducts();
 
		// Assert
		assertNotNull(product);
		assertEquals(products, result);
 
	}
 
//	@Test
//	public void testUpdateProductFailure() throws ProductNotFoundException {
//	    
//	        // Arrange
//	        when(repo.findById(1L)).thenReturn(Optional.empty());
//
//	        // Act
//	        assertNotNull(product);
//	        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, product));
//
//	        // Assert
//	       verify(loggers).error(QUERYMAPPER.RECORD_DOES_NOT_EXITS);
//	    }
	@Test
	public void testUpdateProductFailure() {
	    // Arrange
	    when(repo.findById(1L)).thenReturn(Optional.empty());
 
	    // Act and Assert
	    assertNotNull(product);
	    assertThrows(ProductNotFoundException.class, () -> {
	        productService.updateProduct(1L, product);
	    }, "Product not found with id: 1");
 
	    
	}

 
 
	@Test
	void testUpdateProductSuccess() throws ProductNotFoundException {
 
		Product existingProduct = product;
		existingProduct.setId(2L);
		when(repo.findById(2L)).thenReturn(Optional.of(existingProduct));
 
		Product updatedProduct = new Product();
		updatedProduct.setId(2L);
		updatedProduct.setName("UpdatedProduct");
 
		productService.updateProduct(2L, updatedProduct);
		verify(repo).save(updatedProduct);
	}
 
	@Test
	public void testDeleteSuccess() throws ProductNotFoundException {
 
		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.of(product));
 
		// Act
		String result = productService.deleteProductid(1L);
 
		// Assert
		assertNotNull(product);
		assertEquals(QUERYMAPPER.RECORD_DELETED_SUCCESSFULLY, result);
	}
 
	@Test
	public void testDeleteProductFailure() throws ProductNotFoundException {
 
		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.empty());
 
		// Act
		assertNotNull(product);
		assertThrows(ProductNotFoundException.class, () -> productService.deleteProductid(1L));
 
	}
}