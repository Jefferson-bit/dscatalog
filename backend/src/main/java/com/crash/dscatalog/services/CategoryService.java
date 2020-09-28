package com.crash.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crash.dscatalog.domains.Category;
import com.crash.dscatalog.dto.CategoryDTO;
import com.crash.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	  
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		return  list.stream().map(obj -> new CategoryDTO(obj)).collect(Collectors.toList());
		
	}
	
}
