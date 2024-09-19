package com.prodapt.cmsprojectmain.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.exceptions.ParameterNotFoundException;
import com.prodapt.cmsprojectmain.repositories.ParameterRepository;
import com.prodapt.cmsprojectmain.utility.QUERYMAPPER;

@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    private ParameterRepository repo;
    
    private static final Logger loggers= LoggerFactory.getLogger(ParameterServiceImpl.class);

    @Override
    public Parameter createParameter(Parameter parameter) {
        return repo.save(parameter);
    }

	@Override
	public String deleteParameterById(Long parameterId) throws ParameterNotFoundException{
		Optional<Parameter>deleteparameter=repo.findById(parameterId);
		if(deleteparameter.isPresent()) {
			repo.deleteById(parameterId);
			loggers.info(QUERYMAPPER.RECORD_EXITS);
			return "Parameter deleted sucessfully";
			
		}else {
			loggers.error(QUERYMAPPER.RECORD_DOES_NOT_EXITS);
			throw new ParameterNotFoundException();
		}
	}
			
	}


