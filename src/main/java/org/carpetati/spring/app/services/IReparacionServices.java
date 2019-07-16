package org.carpetati.spring.app.services;

import java.util.List;

import org.carpetati.spring.app.entity.ItemReparacion;
import org.carpetati.spring.app.entity.Reparacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReparacionServices {
	
	public List<Reparacion> findAll();
	public Page<Reparacion> findAll(Pageable p);
	public void save(Reparacion reparacion);
	public void delete(Long id);
	public void delete(Reparacion reparacion);
	public Reparacion findById(Long id);
	
	public void eliminaritem(ItemReparacion i);
	public ItemReparacion finditem(Long id);
	public void guardaritem(ItemReparacion i);
	
}
