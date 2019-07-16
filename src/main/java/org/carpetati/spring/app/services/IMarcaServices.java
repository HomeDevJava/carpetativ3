package org.carpetati.spring.app.services;

import java.util.List;

import org.carpetati.spring.app.entity.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMarcaServices {

	public List<Marca> findAll();
	
	public Page<Marca> findAll(Pageable p);
	
	public void save(Marca marca);
	
	public void delete(Long id);
	
	public void delete(Marca marca);
	
	public Marca findById(Long id);
}