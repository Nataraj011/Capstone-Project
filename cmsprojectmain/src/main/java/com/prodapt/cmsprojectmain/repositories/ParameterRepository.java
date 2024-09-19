package com.prodapt.cmsprojectmain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.cmsprojectmain.entities.Parameter;


public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    // You can add custom query methods if needed	
}