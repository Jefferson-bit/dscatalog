package com.crash.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crash.dscatalog.domains.Product;
import com.crash.dscatalog.dto.ProductDTO;
import com.crash.dscatalog.repositories.ProductRepository;
import com.crash.dscatalog.services.exceptions.DataBaseException;
import com.crash.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = productRepository.findAll(pageRequest);
		return list.map(obj -> new ProductDTO(obj));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("ID not found" + id));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(ProductDTO dto, Long id) {
		try {
			Product entity = productRepository.getOne(id);
			//entity.setName(dto.getName());
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found" + id);
		}
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}
}
