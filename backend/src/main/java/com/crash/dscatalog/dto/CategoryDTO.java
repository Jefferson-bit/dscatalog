package com.crash.dscatalog.dto;

import java.io.Serializable;

import com.crash.dscatalog.domains.Category;

public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	
	private Long id;
	private String name;
	
	public CategoryDTO() {
	}
	
	public CategoryDTO(Category obj) {
		id = obj.getId();
		name= obj.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
