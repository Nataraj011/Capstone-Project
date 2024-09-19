package com.prodapt.cmsprojectmain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.repositories.ProductRepository;
import com.prodapt.cmsprojectmain.utility.QUERYMAPPER;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger loggers = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository repo;

	@Override
	public Product createproduct(Product product) {

		return repo.save(product);
	}

	@Override
	public Product getProductById(Long productId) throws ProductNotFoundException {
		Optional<Product> product = repo.findById(productId);
		if (product.isPresent()) {
			loggers.info(QUERYMAPPER.RECORD_EXITS);
			return product.get();
		} else {
			loggers.error(QUERYMAPPER.RECORD_DOES_NOT_EXITS);
			throw new ProductNotFoundException(" Product " + productId + " doesn't exists");

		}
	}

	@Override
	public Product getProductByName(String name) throws ProductNotFoundException {
		Optional<Product> product = repo.findByName(name);
		if (product.isPresent()) {
			loggers.info(QUERYMAPPER.RECORD_EXITS);
			return product.get();
		} else {
			loggers.error(QUERYMAPPER.RECORD_DOES_NOT_EXITS);
			throw new ProductNotFoundException(" Product " + name + " doesn't exists");

		}
	}

	public List<Product> getAllProducts() {
		loggers.info("Fetching all products");
		List<Product> products = new ArrayList<>();
		repo.findAll().forEach(products::add);
		return products;
	}

	@Override
	public Product updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException {

		Optional<Product> existingProductOptional = repo.findById(id);
		
		if (existingProductOptional.isPresent()) {
			loggers.info(QUERYMAPPER.RECORD_EXITS);
			return repo.save(updatedProduct);

		} else {
			loggers.error(QUERYMAPPER.RECORD_DOES_NOT_EXITS);
			throw new ProductNotFoundException("Product not found with id: " + id);
		}

	}

	@Override
	public String deleteProductid(Long id) throws ProductNotFoundException {
		Optional<Product> deleteproduct = repo.findById(id);
		if (deleteproduct.isPresent()) {
			repo.deleteById(id);
			loggers.info(QUERYMAPPER.RECORD_EXITS);
			return QUERYMAPPER.RECORD_DELETED_SUCCESSFULLY;

		} else {
			loggers.error(QUERYMAPPER.RECORD_DOES_NOT_EXITS);
			throw new ProductNotFoundException();
		}
	}

}
