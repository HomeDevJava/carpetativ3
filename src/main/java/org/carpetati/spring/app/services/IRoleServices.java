package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleServices {

	public List<Role> findAll();
	public void save(Role Role);
	public void delete(Long id);
	public void delete(Role Role);
	public Role findById(Long id);
	public Page<Role> findAll(Pageable p);
}
