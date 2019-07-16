package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.TipoProblema;

public interface ITipoProblemaServices {

	public List<TipoProblema> findAll();

	public void save(TipoProblema TipoProblema);

	public void delete(Long id);

	public void delete(TipoProblema TipoProblema);

	public TipoProblema findById(Long id);
}