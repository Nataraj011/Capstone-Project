package com.prodapt.cmsprojectmain.service;

import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.exceptions.ParameterNotFoundException;

public interface ParameterService {
    public Parameter createParameter(Parameter parameter);
    
    public String deleteParameterById(Long parameterId)throws ParameterNotFoundException;
}
