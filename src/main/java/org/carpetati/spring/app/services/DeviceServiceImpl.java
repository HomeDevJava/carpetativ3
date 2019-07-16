package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IDeviceDao;
import org.carpetati.spring.app.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceServiceImpl implements IDeviceServices{
	
	@Autowired IDeviceDao deviceDao;

	@Transactional(readOnly=true)
	@Override
	public List<Device> findAll() {
		return deviceDao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Device> findAll(Pageable p) {
		return deviceDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Device device) {
		deviceDao.save(device);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		deviceDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Device device) {
		deviceDao.delete(device);		
	}

	@Transactional(readOnly=true)
	@Override
	public Device findById(Long id) {
		return deviceDao.findById(id).orElse(null);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Device> findByNombre(String n) {
		return deviceDao.findByNombre(n);
	}

	

}