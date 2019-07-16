package org.carpetati.spring.app.services;

import java.util.List;
import java.util.Map;
import org.carpetati.spring.app.entity.ItemMovactivo;
import org.carpetati.spring.app.entity.Movactivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMovactivoServices {
	public List<Movactivo> findAll();
	
	public Page<Movactivo> findAll(Pageable p);

	public void save(Movactivo movactivo);

	public void delete(Long id);

	public void delete(Movactivo movactivo);

	public Movactivo findById(Long id);
	
	public void borrarItem(ItemMovactivo m);
	
	public  ItemMovactivo findItem(Long id);
	
	public void guardarItem(ItemMovactivo m);
	
	public Map<String, Object> report(Long id);
}
