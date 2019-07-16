package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmpleadoServices {

	public List<Empleado> findAll();

	public void save(Empleado empleado);

	public void delete(Long id);

	public void delete(Empleado empleado);

	public Empleado findById(Long id);

	public Page<Empleado> findAll(Pageable p);
}