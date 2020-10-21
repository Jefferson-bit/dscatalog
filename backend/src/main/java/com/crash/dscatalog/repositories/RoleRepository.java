package com.crash.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.dscatalog.domains.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
