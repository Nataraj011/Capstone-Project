package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.repositories.RoleRepository;
@SpringBootTest
class RoleServiceImplTest {
 
	@Mock
	private RoleRepository repo;
 
	@InjectMocks
	private RoleServiceImpl roleService;
 
	private Role role;
 
	@BeforeEach
	public void setup() {
		role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_ADMIN);
	}
 
	@Test
	void testFindRoleByName() {
 
		// Arrange
		when(repo.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(role));
 
		// Act
		Optional<Role> result = roleService.findRoleByName(ERole.ROLE_ADMIN);
 
		// Assert
		assertNotNull(role);
		assertEquals(Optional.of(role), result);
 
	}
 
	@Test
	void testFindRoleById() {
 
		// Arrange
		when(repo.findById(1)).thenReturn(Optional.of(role));
 
		// Act
		Optional<Role> result = roleService.findRoleById(1);
 
		// Assert
		assertNotNull(role);
		assertEquals(Optional.of(role), result);
 
	}
	
	
	 @Test
	    public void testGetAllRole() {
	        // Mock data
	        Role role1 = new Role(/* constructor arguments */);
	        Role role2 = new Role(/* constructor arguments */);
	        List<Role> mockRoles = Arrays.asList(role1, role2);

	        // Mock repository behavior
	        when(repo.findAll()).thenReturn(mockRoles);

	        // Call the service method
	        Iterable<Role> result = roleService.getAllRole();

	        // Verify the result
	        assertEquals(2, ((List<Role>) result).size()); // Ensure two roles are returned
	        assertEquals(role1, ((List<Role>) result).get(0)); // Ensure the first role is as expected

	        // Additional assertions as needed
	    }
}