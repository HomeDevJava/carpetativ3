package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.ITipoSiniestroDao;
import org.carpetati.spring.app.entity.TipoSiniestro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoSiniestroServiceImpl implements ITipoSiniestroServices{

	@Autowired ITipoSiniestroDao tipoSiniestroDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<TipoSiniestro> findAll() {
		return tipoSiniestroDao.findAll();
	}

	@Transactional
	@Override
	public void save(TipoSiniestro tipoSiniestro) {
		tipoSiniestroDao.save(tipoSiniestro);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		tipoSiniestroDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(TipoSiniestro tipoSiniestro) {
		tipoSiniestroDao.delete(tipoSiniestro);		
	}

	@Transactional(readOnly=true)
	@Override
	public TipoSiniestro findById(Long id) {
		return tipoSiniestroDao.findById(id).orElse(null);
	}

}