package com.crash.dscatalog.tests.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.crash.dscatalog.dto.ProductDTO;
import com.crash.dscatalog.services.ProductService;
import com.crash.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;

	private long existingId;
	private long nonExistingId;
	private long countTotalProduct;
	private long countPCGamerProducts;
	private PageRequest pageRequest;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1;
		nonExistingId = 1000L;
		countTotalProduct = 25L;
		countPCGamerProducts = 21L;
		pageRequest = PageRequest.of(0, 10);
	}

	@Test
	public void findAllPagedShouldReturnNothingWhenNameDoesNotExists() {
		String name = "Camera";
		Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void findAllPagedShouldReturnProductsWhenNameIsEmpty() {
		String name = "";
		Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProduct, result.getTotalElements());
	}

	@Test
	public void findAllPagedShouldReturnProductsWhenNameExistsIgnoringCase() {
		String name = "pc gAmEr";
		Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}

	@Test
	public void deleteShouldResourceNotFoundExceptionWhenIdExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

	}

	@Test
	public void deleteShouldIdDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
	}
}