package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.dao.IEmpleadoDao;
import org.carpetati.spring.app.entity.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoServiceImpl implements IEmpleadoServices {

	@Autowired IEmpleadoDao empleadoDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Empleado> findAll() {
		return empleadoDao.selectAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Empleado> findAll(Pageable p) {
		return empleadoDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Empleado empleado) {
		empleadoDao.save(empleado);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		empleadoDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(Empleado empleado) {
		empleadoDao.delete(empleado);
	}

	@Transactional(readOnly=true)
	@Override
	public Empleado findById(Long id) {
		return empleadoDao.findById(id).orElse(null);
	}

}