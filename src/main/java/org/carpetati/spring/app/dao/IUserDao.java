package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);

}
