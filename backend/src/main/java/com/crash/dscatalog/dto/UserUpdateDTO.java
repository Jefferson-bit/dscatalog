package com.crash.dscatalog.dto;

import com.crash.dscatalog.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
	private static final long serialVersionUID = 1L;
	
	public UserUpdateDTO() {
		super();
	}
}
