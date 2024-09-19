package com.prodapt.cmsprojectmain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.service.ProductService;
@SpringBootTest
class CustomerControllerTest {
 
	@Mock
	private ProductService productService;
 
	@InjectMocks
	private CustomerController customerController;
 
	private Product product;
	private List<Product> products;
 
	@BeforeEach
	public void setup() {
		product = new Product();
		product.setName("Test Product");
 
		products = new ArrayList<>();
		products.add(product);
	}
 
	@Test
	void testGetProductByName_Success() throws ProductNotFoundException {
			when(productService.getProductByName("Test Product")).thenReturn(product);
 
			ResponseEntity<Product> response = customerController.getProductByName("Test Product");
			assertNotNull(product);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(product, response.getBody());
		}
	@Test
	void testGetProductByName_Failure() throws ProductNotFoundException {
			when(productService.getProductByName("Test Product"))
					.thenThrow(new ProductNotFoundException("Product not found"));
			assertNotNull(product);
			assertThrows(ProductNotFoundException.class, () -> customerController.getProductByName("Test Product"));
		}
	@Test
	void testGetAllProducts() {
		List<Product> products = new ArrayList<>();
		products.add(product);
 
		when(productService.getAllProducts()).thenReturn(products);
 
		ResponseEntity<List<Product>> response = customerController.getAllProducts();
		assertNotNull(product);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(products, response.getBody());
}
}