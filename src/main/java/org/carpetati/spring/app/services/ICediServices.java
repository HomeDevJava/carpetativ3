package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Cedi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICediServices {

	public List<Cedi> findAll();
	public void save(Cedi cedi);
	public void delete(Long id);
	public void delete(Cedi cedi);
	public Cedi findById(Long id);
	public Page<Cedi> findAll(Pageable p);
	
}