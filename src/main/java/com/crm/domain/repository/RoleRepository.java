package com.crm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.domain.model.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	
}
