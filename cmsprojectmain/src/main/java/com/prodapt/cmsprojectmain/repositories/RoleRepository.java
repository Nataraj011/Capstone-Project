package com.prodapt.cmsprojectmain.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
  Optional<Role> findByName(ERole name);
}
