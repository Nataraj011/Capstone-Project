package com.prodapt.cmsprojectmain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.UserNotFoundException;
import com.prodapt.cmsprojectmain.repositories.UserRepository;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	@Autowired
	UserRepository repo;

	@Override
	public String updateRole(Integer userId, Role role) throws UserNotFoundException {
		Optional<UserEntity> user = repo.findById(userId);
		if (user.isPresent()) {
			user.get().setRole(role);
			repo.save(user.get());
			return "Role Updated Successfully!!!";

		} else {
			throw new UserNotFoundException("User with" + userId + "not found");
		}
	}

	@Override
	public UserEntity addUserEntity(UserEntity user) {
		UserEntity userEntity = repo.save(user);
		return userEntity;
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		Optional<UserEntity> user = repo.findByUsername(username);
		return user;
	}

	@Override
	public Boolean existsByUsername(String username) {
		Boolean b = repo.existsByUsername(username);
		return b;
	}

	@Override
	public Optional<UserEntity> findByRole(ERole role) {
		Optional<UserEntity> user = repo.findByRole(role);
		return user;
	}

	@Override
	public UserEntity getUserEntityById(Integer id) throws UserNotFoundException {
		return repo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
	}

	public void setRepo(UserRepository userRepository) {
		this.repo = userRepository;
	}

	@Override
	public Optional<UserEntity> findById(Integer id) throws UserNotFoundException {
		Optional<UserEntity> user = repo.findById(id);
		return user;
	}

	@Override
	 public Iterable<UserEntity> getAllUsers() {
	        return repo.findAll();
	    }
}
