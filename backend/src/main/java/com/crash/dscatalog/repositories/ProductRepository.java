package com.crash.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crash.dscatalog.domains.Category;
import com.crash.dscatalog.domains.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	 @Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
	 		+ "(COALESCE(:categories) IS NULL OR cats IN :categories) AND "
	 		+ "(LOWER(obj.name) LIKE  LOWER(CONCAT('%',:name,'%')) )") 
	 Page<Product> search(List<Category> categories, String name, Pageable pageable); 
}
