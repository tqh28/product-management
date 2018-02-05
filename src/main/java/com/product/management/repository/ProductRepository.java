package com.product.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.product.management.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public Product findByCode(String code);

	public Page<Product> findBySearchNameContainingAndPriceLessThanEqualAndPriceGreaterThanEqual(
			String searchName, Double highestPrice, Double lowestPrice, Pageable pageable);
	
}
