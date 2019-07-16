package org.carpetati.spring.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.carpetati.spring.app.dao.IItemMovactivoDao;
import org.carpetati.spring.app.dao.IMovactivoDao;
import org.carpetati.spring.app.entity.ItemMovactivo;
import org.carpetati.spring.app.entity.Movactivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovactivoServiceImpl implements IMovactivoServices{
	
	@Autowired private IMovactivoDao movactivoDao;
	@Autowired private IItemMovactivoDao itemMovactivoDao;

	@Transactional(readOnly=true)
	@Override
	public List<Movactivo> findAll() {
		return movactivoDao.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Page<Movactivo> findAll(Pageable p) {
		return movactivoDao.findAll(p);
	}

	@Transactional
	@Override
	public void save(Movactivo movactivo) {
		movactivoDao.save(movactivo);
	}

	@Transactional 	@Override
	public void delete(Long id) {
		movactivoDao.deleteById(id);
	}

	@Transactional 	@Override
	public void delete(Movactivo movactivo) {
		movactivoDao.delete(movactivo);
	}

	@Transactional(readOnly=true)
	@Override
	public Movactivo findById(Long id) {
		return movactivoDao.findById(id).orElse(null);
	}

	@Transactional 	@Override
	public void borrarItem(ItemMovactivo m) {
		itemMovactivoDao.delete(m);
	}

	@Transactional(readOnly=true)
	@Override
	public ItemMovactivo findItem(Long id) {
		return itemMovactivoDao.findById(id).orElse(null);
	}

	@Transactional 	@Override
	public void guardarItem(ItemMovactivo m) {
		itemMovactivoDao.save(m);
	}
	 
	@Transactional
	@Override
	public Map<String, Object> report(Long id){
		//List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Movactivo mov= movactivoDao.findById(id).orElse(null);
		Map<String, Object> item = new HashMap<String, Object>();
		
		List<String> listaSeries= new ArrayList<String>();
		List<String> listaActivos= new ArrayList<String>();
		List<String> listaEquipos = new ArrayList<String>();
		mov.getItems().forEach(p ->{
			listaActivos.add(p.getEquipo().getAfijo());
			listaSeries.add(p.getEquipo().getSerie());
			listaEquipos.add(p.getEquipo().getModelo().getNombre());
		});
		
		item.put("mov", mov);
		item.put("id", mov.getId());
		item.put("fecha", mov.getFecha());
		item.put("empleado", mov.getEmpleado().getNombre() + mov.getEmpleado().getApellidos());
		item.put("series", listaSeries.toString());
		item.put("origen", mov.getOrigen().getNombre());
		item.put("destino", mov.getDestino().getNombre());
		item.put("domicilio", mov.getDestino().getDomicilio());
		item.put("activos", listaActivos.toString());
		item.put("modelos", listaEquipos.toString());
		
		return item;
		
	}

}