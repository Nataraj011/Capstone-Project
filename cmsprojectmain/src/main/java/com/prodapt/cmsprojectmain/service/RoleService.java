package com.prodapt.cmsprojectmain.service;

import java.util.Optional;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;


public interface RoleService {
	
	public Optional<Role> findRoleByName(ERole role);
	
	public Optional<Role> findRoleById(Integer id);
	
	
	public Iterable<Role> getAllRole();

}