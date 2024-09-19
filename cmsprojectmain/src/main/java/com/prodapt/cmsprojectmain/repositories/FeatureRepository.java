package com.prodapt.cmsprojectmain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.cmsprojectmain.entities.Features;

@Repository
public interface FeatureRepository extends CrudRepository<Features, Long> {
	public Optional<Features> findById(Long Id);
	 List<Features> findByProductId(Long productId);
	
	
}
