package com.prodapt.cmsprojectmain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.cmsprojectmain.entities.Quotation;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
	
}
