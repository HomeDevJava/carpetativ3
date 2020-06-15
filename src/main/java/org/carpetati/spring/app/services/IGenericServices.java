package org.carpetati.spring.app.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenericServices<T> {
	
	public List<T> findAll();
	public void save(T entidad);
	public void delete(Long id);
	public void delete(T entidad);
	public T findById(Long id);
	public Page<T> findAll(Pageable p);

}
