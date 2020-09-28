package com.crash.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.dscatalog.domains.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
