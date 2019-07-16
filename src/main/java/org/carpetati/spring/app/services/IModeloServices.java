package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IModeloServices {
	public List<Modelo> findAll();

	public void save(Modelo modelo);

	public void delete(Long id);

	public void delete(Modelo modelo);

	public Modelo findById(Long id);

	public Page<Modelo> findAll(Pageable p);
}