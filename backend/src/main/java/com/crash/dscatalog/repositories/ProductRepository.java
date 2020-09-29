package com.crash.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.dscatalog.domains.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
