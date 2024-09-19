package com.prodapt.cmsprojectmain.service;

import java.util.List;

import com.prodapt.cmsprojectmain.entities.Features;

import com.prodapt.cmsprojectmain.exceptions.FeatureNotFoundException;


public interface FeatureService {
	public Features createFeature(Features feature);
	public String deleteFeatureById(Long featureId) throws FeatureNotFoundException;
	public Features getFeatureById(Long eatureId) throws  FeatureNotFoundException;
	public List<Features> getFeaturesByProductId(Long productId);

}
