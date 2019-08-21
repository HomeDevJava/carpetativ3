package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Siniestro;
import org.carpetati.spring.app.entity.ItemSiniestro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ISiniestrosServices {
	
	public List<Siniestro> findAll();
	public Page<Siniestro> findAll(Pageable p);
	public void save(Siniestro siniestro);
	public void save(Siniestro siniestro, List<MultipartFile> files);
	public void delete(Long id);
	public void delete(Siniestro siniestro);
	public Siniestro findById(Long id);
	
	public void borrarItem(ItemSiniestro m);
	
	public  ItemSiniestro findItem(Long id);
	
	public void guardarItem(ItemSiniestro m);
	
}