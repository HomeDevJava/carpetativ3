package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEquipoServices {
	public List<Equipo> findAll();
	
	public Page<Equipo> findAll(Pageable p);
	
	public void save(Equipo Equipo);
	
	public void delete(Long id);
	
	public void delete(Equipo Equipo);
	
	public Equipo findById(Long id);
	
	public List<Equipo> findBySerie(String serie);
	
	public List<Equipo> findBySerieLikeIgnoreCase(String serie);

	public Page<Equipo> findBySerieLike(String serie, String afijo, Pageable p);

}