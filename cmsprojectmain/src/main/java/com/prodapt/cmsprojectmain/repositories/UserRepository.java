package com.prodapt.cmsprojectmain.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

	public Optional<UserEntity> findByRole(ERole role);
	public Optional<UserEntity> findById(Integer id );
}
