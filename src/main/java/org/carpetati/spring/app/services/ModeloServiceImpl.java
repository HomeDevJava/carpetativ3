package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IModeloDao;
import org.carpetati.spring.app.entity.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModeloServiceImpl implements IModeloServices {

	@Autowired private IModeloDao modeloDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Modelo> findAll() {
		return modeloDao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Modelo> findAll(Pageable p) {
		return modeloDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Modelo modelo) {
		modeloDao.save(modelo);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		modeloDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Modelo modelo) {
		modeloDao.delete(modelo);		
	}

	@Transactional(readOnly=true)
	@Override
	public Modelo findById(Long id) {
		return modeloDao.findById(id).orElse(null);
	}

}