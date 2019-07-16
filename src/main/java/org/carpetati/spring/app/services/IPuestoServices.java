package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Puesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPuestoServices {
	public List<Puesto> findAll();

	public void save(Puesto puesto);

	public void delete(Long id);

	public void delete(Puesto puesto);

	public Puesto findById(Long id);

	public Page<Puesto> findAll(Pageable p);
}
