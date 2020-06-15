package org.carpetati.spring.app.services;

import java.util.List;

import org.carpetati.spring.app.dao.IRoleDao;
import org.carpetati.spring.app.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements IGenericServices<Role>{
	
	@Autowired private IRoleDao roleDao;

	@Transactional(readOnly = true)
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Transactional
	@Override
	public void save(Role role) {
		roleDao.save(role);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		roleDao.deleteById(id);		
	}

	@Transactional
	@Override
	public void delete(Role role) {
		roleDao.delete(role);		
	}

	@Transactional
	@Override
	public Role findById(Long id) {
		return roleDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Role> findAll(Pageable p) {
		return roleDao.findAll(p);
	}

}
