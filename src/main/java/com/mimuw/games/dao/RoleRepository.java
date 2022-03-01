package com.mimuw.games.dao;

import com.mimuw.games.entity.security.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    
    @Query("Select r from Role r where r.name = ?1")
    Role findByName(String name);
}
