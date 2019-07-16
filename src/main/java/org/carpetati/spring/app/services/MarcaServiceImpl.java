package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IMarcaDao;
import org.carpetati.spring.app.entity.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarcaServiceImpl implements IMarcaServices{
	
	@Autowired private IMarcaDao marcaDao;

	@Transactional(readOnly=true)
	@Override
	public List<Marca> findAll() {
		return marcaDao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Marca> findAll(Pageable p) {
		return marcaDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Marca marca) {
		marcaDao.save(marca);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		marcaDao.deleteById(id);
	}
	
	@Transactional
	@Override
	public void delete(Marca marca) {
		marcaDao.delete(marca);		
	}

	@Transactional(readOnly=true)
	@Override
	public Marca findById(Long id) {
		return marcaDao.findById(id).orElse(null);
	}	

}