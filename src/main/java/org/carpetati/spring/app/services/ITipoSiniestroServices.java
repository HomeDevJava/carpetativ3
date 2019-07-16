package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.TipoSiniestro;

public interface ITipoSiniestroServices {

	public List<TipoSiniestro> findAll();

	public void save(TipoSiniestro tipoSiniestro);

	public void delete(Long id);

	public void delete(TipoSiniestro tipoSiniestro);

	public TipoSiniestro findById(Long id);
}