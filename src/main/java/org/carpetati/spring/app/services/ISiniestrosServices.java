package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Siniestros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISiniestrosServices {
	
	public List<Siniestros> findAll();
	public Page<Siniestros> findAll(Pageable p);
	public void save(Siniestros siniestro);
	public void delete(Long id);
	public void delete(Siniestros siniestro);
	public Siniestros findById(Long id);
	
}