package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.exceptions.ParameterNotFoundException;
import com.prodapt.cmsprojectmain.repositories.ParameterRepository;
 
@SpringBootTest
class ParameterServiceImplTest {
 
	@Mock
	private ParameterRepository repo;
 
	@InjectMocks
	private ParameterServiceImpl parameterService;
 
	private Parameter parameter;
 
	@BeforeEach
	public void setup() {
		parameter = new Parameter();
		parameter.setId(1L);
		parameter.setName("Test Parameter");
		parameter.setType("Test Type");
		parameter.setValue("Test Value");
	}
 
	@Test
	void testCreateParameterSuccess() {
		// Arrange
 
		when(repo.save(parameter)).thenReturn(parameter);
 
		// Act
		Parameter result = parameterService.createParameter(parameter);
 
		// Assert
		assertNotNull(result);
		assertEquals(parameter, result);
	}
 
	@Test
	void testCreateParameterFailure() {
		// Arrange
 
		when(repo.save(parameter)).thenThrow(new RuntimeException("Error saving parameter"));
 
		// Act and Assert
		assertThrows(RuntimeException.class, () -> {
			parameterService.createParameter(parameter);
		});
	}
 
	@Test
	void testDeleteParameterByIdSuccess() throws ParameterNotFoundException {
		// Arrange
		Long parameterId = 1L;
		when(repo.findById(parameterId)).thenReturn(Optional.of(new Parameter()));
 
		// Act
		String result = parameterService.deleteParameterById(parameterId);
 
		// Assert
		assertEquals("Parameter deleted sucessfully", result);
	}
 
	@Test
	void testDeleteParameterByIdFailure() {
		// Arrange
		Long parameterId = 1L;
		when(repo.findById(parameterId)).thenReturn(Optional.empty());
 
		// Act and Assert
		assertThrows(ParameterNotFoundException.class, () -> {
			parameterService.deleteParameterById(parameterId);
		});
	}
}