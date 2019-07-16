package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IPuestoDao;
import org.carpetati.spring.app.entity.Puesto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuestoServiceImpl implements IPuestoServices {

	@Autowired
	private IPuestoDao puestoDao;

	@Transactional(readOnly = true)
	@Override
	public List<Puesto> findAll() {
		return puestoDao.findAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Puesto> findAll(Pageable p) {
		return puestoDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Puesto puesto) {
		puestoDao.save(puesto);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		puestoDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Puesto puesto) {
		puestoDao.delete(puesto);
	}

	@Transactional(readOnly = true)
	@Override
	public Puesto findById(Long id) {
		return puestoDao.findById(id).orElse(null);
	}
}
