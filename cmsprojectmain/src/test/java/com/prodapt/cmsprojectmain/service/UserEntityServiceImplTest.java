package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.MockitoAnnotations;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.UserNotFoundException;
import com.prodapt.cmsprojectmain.repositories.UserRepository;
 
class UserEntityServiceTest {
	@Mock
	private UserRepository userRepository;
 
	@InjectMocks
	private UserEntityServiceImpl userEntityService;
 
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
 
	@Test
	void testUpdateRole_Success() throws UserNotFoundException {
		Integer userId = 1;
		Role newRole = new Role(1, ERole.ROLE_ADMIN);
		UserEntity user = new UserEntity();
		user.setId(userId);
		user.setRole(new Role(4, ERole.ROLE_CUSTOMER));
 
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userRepository.save(any(UserEntity.class))).thenReturn(user);
 
		String result = userEntityService.updateRole(userId, newRole);
 
		assertEquals("Role Updated Successfully!!!", result);
		assertEquals(newRole, user.getRole());
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, times(1)).save(user);
	}
 
	@Test
	void testUpdateRole_UserNotFound() throws UserNotFoundException {
		Integer userId = 1;
		Role newRole = new Role(1, ERole.ROLE_ADMIN);
 
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
 
		assertThrows(UserNotFoundException.class, () -> userEntityService.updateRole(userId, newRole));
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, times(0)).save(any(UserEntity.class));
	}
 
	@Test
	void testAddUserEntity_Success() {
		UserEntity user = new UserEntity();
		user.setUsername("testuser");
 
		when(userRepository.save(any(UserEntity.class))).thenReturn(user);
 
		UserEntity result = userEntityService.addUserEntity(user);
 
		assertEquals(user, result);
		verify(userRepository, times(1)).save(user);
	}
 
	@Test
	void testFindByUsername_Success() {
		String username = "testuser";
		UserEntity user = new UserEntity();
		user.setUsername(username);
 
		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
 
		Optional<UserEntity> result = userEntityService.findByUsername(username);
 
		assertTrue(result.isPresent());
		assertEquals(user, result.get());
		verify(userRepository, times(1)).findByUsername(username);
	}
 
	@Test
	void testFindByUsername_NotFound() {
		String username = "testuser";
 
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
 
		Optional<UserEntity> result = userEntityService.findByUsername(username);
 
		assertFalse(result.isPresent());
		verify(userRepository, times(1)).findByUsername(username);
	}
 
	@Test
	void testExistsByUsername_True() {
		String username = "testuser";
 
		when(userRepository.existsByUsername(username)).thenReturn(true);
 
		Boolean result = userEntityService.existsByUsername(username);
 
		assertTrue(result);
		verify(userRepository, times(1)).existsByUsername(username);
	}
 
	@Test
	void testExistsByUsername_False() {
		String username = "testuser";
 
		when(userRepository.existsByUsername(username)).thenReturn(false);
 
		Boolean result = userEntityService.existsByUsername(username);
 
		assertFalse(result);
		verify(userRepository, times(1)).existsByUsername(username);
	}
 
	@Test
	void testFindByRole_Success() {
		ERole role = ERole.ROLE_CUSTOMER;
		UserEntity user = new UserEntity();
		user.setRole(new Role(4, role));
 
		when(userRepository.findByRole(role)).thenReturn(Optional.of(user));
 
		Optional<UserEntity> result = userEntityService.findByRole(role);
 
		assertTrue(result.isPresent());
		assertEquals(user, result.get());
		verify(userRepository, times(1)).findByRole(role);
	}
 
	@Test
	void testFindByRole_NotFound() {
		ERole role = ERole.ROLE_CUSTOMER;
 
		when(userRepository.findByRole(role)).thenReturn(Optional.empty());
 
		Optional<UserEntity> result = userEntityService.findByRole(role);
 
		assertFalse(result.isPresent());
		verify(userRepository, times(1)).findByRole(role);
	}
 
	@Test
	void testFindbyId_Success() throws UserNotFoundException {
		Integer userId = 1;
		UserEntity user = new UserEntity();
		user.setId(userId);
		user.setUsername("testUser");
		user.setEmail("testEmail");
		user.setPassword("testPassword");
 
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
 
		Optional<UserEntity> result = userEntityService.findById(userId);
 
		assertEquals(Optional.of(user), result);
		verify(userRepository, times(1)).findById(userId);
	}
 
	@Test
	void testGetUserEntityById_Success() throws UserNotFoundException {
		Integer userId = 1;
		UserEntity user = new UserEntity();
		user.setId(userId);
		user.setUsername("testUser");
		user.setEmail("testEmail");
		user.setPassword("testPassword");
 
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
 
		UserEntity result = userEntityService.getUserEntityById(userId);
 
		assertEquals(user, result);
		verify(userRepository, times(1)).findById(userId);
	}
 
	@Test
	void testGetUserEntityById_NotFound() {
		Integer userId = 1;
 
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
 
		assertThrows(UserNotFoundException.class, () -> userEntityService.getUserEntityById(userId));
 
		verify(userRepository, times(1)).findById(userId);
	}
	
	 @Test
	    public void testGetAllUsers_EmptyList() {
	        // Mock repository to return an empty list
	        when(userRepository.findAll()).thenReturn(Arrays.asList());

	        // Call the service method
	        Iterable<UserEntity> result = userEntityService.getAllUsers();

	        // Assert that the result is an empty iterable
	        assertFalse(result.iterator().hasNext(), "Expected empty list");
	    }

	    @Test
	    public void testGetAllUsers_MultipleUsers() {
	        // Mock data
	        UserEntity user1 = new UserEntity(/* constructor arguments */);
	        UserEntity user2 = new UserEntity(/* constructor arguments */);
	        List<UserEntity> mockUsers = Arrays.asList(user1, user2);

	        // Mock repository behavior
	        when(userRepository.findAll()).thenReturn(mockUsers);

	        // Call the service method
	        Iterable<UserEntity> result = userEntityService.getAllUsers();

	        // Assert that the result contains the expected users
	        assertEquals(2, ((List<UserEntity>) result).size(), "Expected two users");
	        assertTrue(((List<UserEntity>) result).contains(user1), "Expected user1 in result");
	        assertTrue(((List<UserEntity>) result).contains(user2), "Expected user2 in result");
	    }
 
}