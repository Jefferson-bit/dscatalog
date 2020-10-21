package com.crash.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.dscatalog.domains.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
