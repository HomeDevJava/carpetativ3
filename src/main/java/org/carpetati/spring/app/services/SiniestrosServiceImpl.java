package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.ISiniestrosDao;
import org.carpetati.spring.app.entity.Siniestros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SiniestrosServiceImpl implements ISiniestrosServices{
	
	@Autowired private ISiniestrosDao siniestroDao;

	@Override
	@Transactional(readOnly = true)
	public List<Siniestros> findAll() {
		return siniestroDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Siniestros> findAll(Pageable p) {
		return siniestroDao.findAll(p);
	}

	@Override
	public void save(Siniestros siniestro) {
		siniestroDao.save(siniestro);		
	}

	@Override
	public void delete(Long id) {
		siniestroDao.deleteById(id);
	}

	@Override
	public void delete(Siniestros siniestro) {
		siniestroDao.delete(siniestro);		
	}

	@Override
	@Transactional(readOnly = true)
	public Siniestros findById(Long id) {
		return siniestroDao.findById(id).orElse(null);
	}

}
