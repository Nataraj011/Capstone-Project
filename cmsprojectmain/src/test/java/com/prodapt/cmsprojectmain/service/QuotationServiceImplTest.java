package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.entities.Quotation;
import com.prodapt.cmsprojectmain.exceptions.QuotationNotFoundException;
import com.prodapt.cmsprojectmain.repositories.QuotationRepository;
import com.prodapt.cmsprojectmain.utility.QUERYMAPPER;
 
@SpringBootTest
public class QuotationServiceImplTest {
 
	@Mock
	private QuotationRepository quotationRepository;
 
	@InjectMocks
	private QuotationServiceImpl quotationService;
 
	private Quotation quotation;
 
	@BeforeEach
	public void setup() {
		quotation = new Quotation();
		quotation.setId(1L);
		
		quotation.setProduct(new Product());
		quotation.setTotalAmount(100.0);
		quotation.setQuantity(2);
	}
 
	@Test
	void testAddQuotationSuccess() {
		// Arrange
		when(quotationRepository.save(quotation)).thenReturn(quotation);
 
		// Act
		Quotation result = quotationService.addQuotation(quotation);
 
		// Assert
		assertNotNull(quotation);
		assertEquals(quotation, result);
	}
 
	@Test
	void testAddQuotationFailure() {
		// Arrange
		when(quotationRepository.save(quotation)).thenThrow(new RuntimeException("Error saving quotation"));
 
		// Act and Assert
		assertThrows(RuntimeException.class, () -> {
			quotationService.addQuotation(quotation);
		});
	}
 
	@Test
	void testGetQuotationByIdSuccess() throws QuotationNotFoundException {
		// Arrange
		when(quotationRepository.findById(1L)).thenReturn(Optional.of(quotation));
 
		// Act
		Quotation result = quotationService.getQuotationById(1L);
 
		// Assert
		assertNotNull(quotation);
		assertEquals(quotation, result);
	}
 
	@Test
	void testGetQuotationByIdFailure() {
		// Arrange
		when(quotationRepository.findById(1L)).thenReturn(Optional.empty());
 
		// Act and Assert
		assertThrows(QuotationNotFoundException.class, () -> {
			quotationService.getQuotationById(1L);
		});
	}
	
	 @Test
	    public void testGetAllQuotations() {
	        // Mock data
	        Quotation quotation1 = new Quotation(/* constructor arguments */);
	        Quotation quotation2 = new Quotation(/* constructor arguments */);
	        List<Quotation> mockQuotations = Arrays.asList(quotation1, quotation2);

	        // Mock repository behavior
	        when(quotationRepository.findAll()).thenReturn(mockQuotations);

	        // Call the service method
	        List<Quotation> result = quotationService.getAllQuotations();

	        // Verify the result
	        assertEquals(2, result.size()); // Ensure two quotations are returned
	        assertEquals(quotation1, result.get(0)); // Ensure the first quotation is as expected

	        // Additional assertions as needed
	    }
 
	 @Test
	 public void testDeleteQuotationById() {
	     // Assume quotationRepository is mocked or injected appropriately
	     
	     // Given
	     Long idToDelete = 1L;
	     Quotation quotationToDelete = new Quotation(); // Create a quotation object with id 1L
	     
	     // Mock repository behavior
	     when(quotationRepository.findById(idToDelete)).thenReturn(Optional.of(quotationToDelete));
	     
	     // When
	     String result = null;
	     try {
	         result = quotationService.deleteQuotionbyid(idToDelete);
	         
	         // Verify that deleteById was called with the correct id
	         verify(quotationRepository, times(1)).deleteById(idToDelete);
	         
	     } catch (QuotationNotFoundException e) {
	         fail("QuotationNotFoundException should not be thrown when the quotation exists.");
	     }
	     
	     // Then
	     assertEquals(QUERYMAPPER.RECORD_DELETED_SUCCESSFULLY, result);
	 }

	 @Test
	 public void testDeleteQuotationById_NotFound() {
	     // Assume quotationRepository is mocked or injected appropriately
	     
	     // Given
	     Long idToDelete = 1L;
	     
	     // Mock repository behavior
	     when(quotationRepository.findById(idToDelete)).thenReturn(Optional.empty());
	     
	     // When
	     try {
	         quotationService.deleteQuotionbyid(idToDelete);
	         fail("QuotationNotFoundException should have been thrown.");
	     } catch (QuotationNotFoundException e) {
	         // QuotationNotFoundException should be caught here
	         // Optionally, you can assert or verify its message or behavior
	     }
	     
	     // Optionally, you might also verify that deleteById was not called in this case
	     verify(quotationRepository, never()).deleteById(any());
	 }

}