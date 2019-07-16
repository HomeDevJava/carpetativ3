package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IEquipoDao;
import org.carpetati.spring.app.entity.Equipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoServiceImpl implements IEquipoServices{

	@Autowired IEquipoDao equipoDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Equipo> findAll() {
		return equipoDao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Equipo> findAll(Pageable p) {
		return equipoDao.findAll(p);
	}	

	@Transactional
	@Override
	public void save(Equipo equipo) {
		equipoDao.save(equipo);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		equipoDao.deleteById(id);
	}
	
	@Transactional
	@Override
	public void delete(Equipo equipo) {
		equipoDao.delete(equipo);		
	}

	@Transactional(readOnly=true)
	@Override
	public Equipo findById(Long id) {
		return equipoDao.findById(id).orElse(null);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Equipo> findBySerie(String serie) {
		return equipoDao.findBySerie(serie);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Equipo> findBySerieLikeIgnoreCase(String serie) {		
		return equipoDao.findBySerieLikeIgnoreCase(serie);
	}

	@Override
	public Page<Equipo> findBySerieLike(String serie, String afijo, Pageable p) {		
		return equipoDao.findBySerieContainingOrAfijoContaining(serie,afijo, p);
	}	
}