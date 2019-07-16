package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IItemReparacion;
import org.carpetati.spring.app.dao.IReparacionDao;
import org.carpetati.spring.app.entity.ItemReparacion;
import org.carpetati.spring.app.entity.Reparacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReparacionServiceImpl implements IReparacionServices{

	@Autowired IReparacionDao reparacionDao;
	@Autowired IItemReparacion itemReparacion; 
	
	@Transactional(readOnly = true)
	@Override
	public List<Reparacion> findAll() {
		return reparacionDao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Reparacion> findAll(Pageable p) {
		return reparacionDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Reparacion Reparacion) {
		reparacionDao.save(Reparacion);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		reparacionDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Reparacion Reparacion) {
		reparacionDao.delete(Reparacion);
	}

	@Transactional(readOnly = true)
	@Override
	public Reparacion findById(Long id) {
		return reparacionDao.findById(id).orElse(null);
	}

	@Override
	public void eliminaritem(ItemReparacion i) {
		itemReparacion.delete(i);		
	}

	@Override
	public ItemReparacion finditem(Long id) {
		return itemReparacion.findById(id).orElse(null);
	}

	@Override
	public void guardaritem(ItemReparacion i) {
		itemReparacion.save(i);		
	}

	
}
