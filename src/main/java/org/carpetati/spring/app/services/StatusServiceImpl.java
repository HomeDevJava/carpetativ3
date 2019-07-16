package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IStatusDao;
import org.carpetati.spring.app.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusServiceImpl implements IStatusServices {
	
	@Autowired IStatusDao statusDao;

	@Transactional(readOnly=true)
	@Override
	public List<Status> findAll() {
		return statusDao.findAll();
	}

	@Transactional
	@Override
	public void save(Status status) {
		statusDao.save(status);		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		statusDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Status status) {
		statusDao.delete(status);		
	}

	@Transactional(readOnly=true)
	@Override
	public Status findById(Long id) {
		return statusDao.findById(id).orElse(null);
	}

}