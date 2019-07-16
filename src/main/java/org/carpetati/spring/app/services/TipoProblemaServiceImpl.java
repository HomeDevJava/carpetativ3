package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.ITipoProblemaDao;
import org.carpetati.spring.app.entity.TipoProblema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoProblemaServiceImpl implements ITipoProblemaServices{

	@Autowired ITipoProblemaDao tipoProblemaDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<TipoProblema> findAll() {
		return tipoProblemaDao.findAll();
	}

	@Transactional
	@Override
	public void save(TipoProblema tipoProblema) {
		tipoProblemaDao.save(tipoProblema);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		tipoProblemaDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(TipoProblema tipoProblema) {
		tipoProblemaDao.delete(tipoProblema);		
	}

	@Transactional(readOnly=true)
	@Override
	public TipoProblema findById(Long id) {
		return tipoProblemaDao.findById(id).orElse(null);
	}

}