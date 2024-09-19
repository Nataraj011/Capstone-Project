package com.prodapt.cmsprojectmain.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.exceptions.FeatureNotFoundException;
import com.prodapt.cmsprojectmain.repositories.FeatureRepository;


@Service
public class FeatureServiceImpl implements FeatureService {

	private static final Logger loggers = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private FeatureRepository repo;

	@Override
	public Features createFeature(Features feature) {

		return repo.save(feature);
	}

	@Override
	public String deleteFeatureById(Long featureId) throws FeatureNotFoundException {
		Optional<Features> deletedFeature = repo.findById(featureId);
		if (deletedFeature.isPresent()) {
			repo.deleteById(featureId);
			loggers.info("Feature with ID " + featureId + " has been deleted.");
			return "Feature deleted successfully";
		} else {
			loggers.info("Feature with ID " + featureId + " does not exist in the record.");
			throw new FeatureNotFoundException();
		}
	}

	@Override
	public Features getFeatureById(Long featureId) throws FeatureNotFoundException {
	    Optional<Features> feature = repo.findById(featureId);
	    if (feature.isPresent()) {
	        
	        return feature.get();
	    } else {
	      
	        throw new FeatureNotFoundException("Feature " + featureId + " doesn't exist");
	    }
	}
	
	  @Override
	    public List<Features> getFeaturesByProductId(Long productId) {
	        // Implement logic to fetch features for the given productId
	        return repo.findByProductId(productId);
	    }
	}



