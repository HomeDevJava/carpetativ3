package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDeviceServices {

	public List<Device> findAll();
	public Page<Device> findAll(Pageable p);

	public void save(Device device);

	public void delete(Long id);

	public void delete(Device device);

	public Device findById(Long id);
	
	public List<Device> findByNombre(String n);
}