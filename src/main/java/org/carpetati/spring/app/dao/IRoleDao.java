package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role, Long>{

}
