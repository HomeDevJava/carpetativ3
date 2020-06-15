package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.ICediDao;
import org.carpetati.spring.app.entity.Cedi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CediServiceImpl implements IGenericServices<Cedi>{

@Autowired private ICediDao cediDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Cedi> findAll() {
		return cediDao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Cedi> findAll(Pageable p) {
		return cediDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Cedi cedi) {
		cediDao.save(cedi);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		cediDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Cedi cedi) {
		cediDao.delete(cedi);		
	}

	@Transactional(readOnly=true)
	@Override
	public Cedi findById(Long id) {
		return cediDao.findById(id).orElse(null);
	}
}
