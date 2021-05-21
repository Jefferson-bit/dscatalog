package com.crash.dscatalog.tests.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crash.dscatalog.domains.Product;
import com.crash.dscatalog.repositories.ProductRepository;
import com.crash.dscatalog.services.ProductService;
import com.crash.dscatalog.services.exceptions.DataBaseException;
import com.crash.dscatalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;
	private PageImpl<Product> page;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependendId;
	private Product product;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId =1;
		nonExistingId = 1000L;
		dependendId = 4L;
		product = new Product();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.search(ArgumentMatchers.any(), ArgumentMatchers.anyString(), ArgumentMatchers.any()))
		.thenReturn(page);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataBaseException.class).when(repository).deleteById(dependendId);
	}
	
	@Test
	public void deleteShouldDataIntegrityViolationExceptionWhenDependendId() {
		Assertions.assertThrows(DataBaseException.class, () -> {
			service.delete(dependendId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependendId);
	}
	
	@Test
	public void deleteShouldResourceNotFoundExceptionWhenIdExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldIdDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(()-> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
}
