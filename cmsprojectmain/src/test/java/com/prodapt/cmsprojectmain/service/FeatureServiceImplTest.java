package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.exceptions.FeatureNotFoundException;
import com.prodapt.cmsprojectmain.repositories.FeatureRepository;


 
@SpringBootTest
class FeatureServiceImplTest {
 
	@Mock
	private FeatureRepository repo;
 
	@InjectMocks
	private FeatureServiceImpl featureService;
 
	private Features feature;
 
	@BeforeEach
	public void setup() {
		feature = new Features();
		feature.setId(1L);
		feature.setName("Test Feature");
	}
 
	@Test
	void testCreateFeatureSuccess() {
		// Arrange
		Features feature = new Features();
		feature.setName("Test Feature");
 
		when(repo.save(feature)).thenReturn(feature);
 
		// Act
		Features result = featureService.createFeature(feature);
 
		// Assert
		assertNotNull(result);
		assertEquals(feature, result);
	}
 
	@Test
	void testCreateFeatureFailure() {
		// Arrange
		Features feature = new Features();
		feature.setName("Test Feature");
 
		when(repo.save(feature)).thenThrow(new RuntimeException("Error saving feature"));
 
		// Act and Assert
		assertThrows(RuntimeException.class, () -> {
			featureService.createFeature(feature);
		});
	}
 
	@Test
	void testDeleteFeatureByIdSuccess() throws FeatureNotFoundException {
		// Arrange
		Long featureId = 1L;
		when(repo.findById(featureId)).thenReturn(Optional.of(new Features()));
 
		// Act
		String result = featureService.deleteFeatureById(featureId);
 
		// Assert
		assertEquals("Feature deleted successfully", result);
	}
 
	@Test
	void testDeleteFeatureByIdFailure() {
		// Arrange
		Long featureId = 1L;
		when(repo.findById(featureId)).thenReturn(Optional.empty());
 
		// Act and Assert
		assertThrows(FeatureNotFoundException.class, () -> {
			featureService.deleteFeatureById(featureId);
		});
	}
	
	@Test
	public void testGetFeatureById_ExistingFeature() {
	    // Prepare test data
	    Long featureId = 1L;
	    Features mockFeature = new Features(/* constructor arguments */);
	    when(repo.findById(featureId)).thenReturn(Optional.of(mockFeature));

	    // Call the service method
	    try {
	        Features result = featureService.getFeatureById(featureId);

	        // Assert that the returned feature matches the mockFeature
	        assertEquals(mockFeature, result, "Returned feature should match mockFeature");
	    } catch (FeatureNotFoundException ex) {
	        fail("Unexpected exception thrown: " + ex.getMessage());
	    }
	}
	
	@Test
	public void testGetFeatureById_ExceptionFromRepository() {
	    // Prepare test data
	    Long featureId = 1L;
	    when(repo.findById(featureId)).thenThrow(new RuntimeException("Repository exception"));

	    // Call the service method and expect RuntimeException or handle accordingly
	    try {
	        featureService.getFeatureById(featureId);
	        fail("Expected RuntimeException was not thrown");
	    } catch (RuntimeException ex) {
	        // Assert or log the exception message as per your error handling strategy
	        assertEquals("Repository exception", ex.getMessage(), "Expected exception message");
	    } catch (FeatureNotFoundException ex) {
	        fail("Unexpected FeatureNotFoundException thrown: " + ex.getMessage());
	    }
	}
	
	@Test
	public void testGetFeatureById_NonExistingFeature() {
	    // Prepare test data
	    Long featureId = 1L;
	    when(repo.findById(featureId)).thenReturn(Optional.empty());

	    // Call the service method and expect FeatureNotFoundException
	    try {
	        featureService.getFeatureById(featureId);
	        fail("Expected FeatureNotFoundException was not thrown");
	    } catch (FeatureNotFoundException ex) {
	        // Assert the exception message or handle as per your application's error handling strategy
	        assertTrue(ex.getMessage().contains(String.valueOf(featureId)), "Exception message should contain featureId");
	    }
	}
	
	@Test
	public void testGetFeaturesByProductId_ExistingFeatures() {
	    // Prepare test data
	    Long productId = 1L;
	    List<Features> expectedFeatures = Arrays.asList(
	        new Features(/* Initialize with relevant data */),
	        new Features(/* Initialize with relevant data */)
	    );
	    when(repo.findByProductId(productId)).thenReturn(expectedFeatures);

	    // Call the service method
	    List<Features> result = featureService.getFeaturesByProductId(productId);

	    // Assert that the returned list of features matches the expected list
	    assertEquals(expectedFeatures.size(), result.size(), "Number of features should match");
	    assertTrue(result.containsAll(expectedFeatures), "Returned features should match expectedFeatures");
	}


	@Test
	public void testGetFeaturesByProductId_NoFeatures() {
	    // Prepare test data
	    Long productId = 1L;
	    when(repo.findByProductId(productId)).thenReturn(Collections.emptyList());

	    // Call the service method
	    List<Features> result = featureService.getFeaturesByProductId(productId);

	    // Assert that the returned list of features is empty
	    assertTrue(result.isEmpty(), "Returned list of features should be empty");
	}


	@Test
	public void testGetFeaturesByProductId_ExceptionFromRepository() {
	    // Prepare test data
	    Long productId = 1L;
	    when(repo.findByProductId(productId)).thenThrow(new RuntimeException("Repository exception"));

	    // Call the service method and expect RuntimeException or handle accordingly
	    try {
	        featureService.getFeaturesByProductId(productId);
	        fail("Expected RuntimeException was not thrown");
	    } catch (RuntimeException ex) {
	        // Assert or log the exception message as per your error handling strategy
	        assertEquals("Repository exception", ex.getMessage(), "Expected exception message");
	    }
	}


}