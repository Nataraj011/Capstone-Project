package com.prodapt.cmsprojectmain.service;

import java.util.Optional;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.UserNotFoundException;

public interface UserEntityService {

	public UserEntity addUserEntity(UserEntity user);

	public String updateRole(Integer userId, Role role)throws UserNotFoundException;

	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Optional<UserEntity> findByRole(ERole role) ;
	
	UserEntity getUserEntityById(Integer id) throws UserNotFoundException; 
	
	public Optional<UserEntity> findById(Integer id) throws UserNotFoundException;
	
	public Iterable<UserEntity> getAllUsers();
	
	
	
	    
}