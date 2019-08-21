package org.carpetati.spring.app.services;

import java.io.IOException;
import java.util.List;

import org.carpetati.spring.app.dao.IItemSiniestroDao;
import org.carpetati.spring.app.dao.ISiniestrosDao;
import org.carpetati.spring.app.entity.Siniestro;
import org.carpetati.spring.app.entity.ItemSiniestro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class SiniestrosServiceImpl implements ISiniestrosServices{
	
	@Autowired private ISiniestrosDao siniestroDao;
	@Autowired private IItemSiniestroDao itemSiniestroDao;

	@Override
	@Transactional(readOnly = true)
	public List<Siniestro> findAll() {
		return siniestroDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Siniestro> findAll(Pageable p) {
		return siniestroDao.findAll(p);
	}

	@Override
	public void save(Siniestro siniestro) {
		siniestroDao.save(siniestro);		
	}

	@Override
	public void delete(Long id) {
		siniestroDao.deleteById(id);
	}

	@Override
	public void delete(Siniestro siniestro) {
		siniestroDao.delete(siniestro);		
	}

	@Override
	@Transactional(readOnly = true)
	public Siniestro findById(Long id) {
		return siniestroDao.findById(id).orElse(null);
	}

	@Override
	public void save(Siniestro siniestro, List<MultipartFile> files) {
		try {
			for (int i = 0; i < files.size(); i++) {
				switch (i) {
				case 0:
					siniestro.setActahechos(files.get(i).getBytes());
					break;
				case 1:
					siniestro.setActamp(files.get(i).getBytes());
					break;
				case 2:
					siniestro.setIfe(files.get(i).getBytes());
					break;
				case 3:
					siniestro.setFormatodesc(files.get(i).getBytes());
					break;
				}
			}
			siniestroDao.save(siniestro);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Transactional @Override
	public void borrarItem(ItemSiniestro m) {
		itemSiniestroDao.delete(m);		
	}

	@Transactional(readOnly = true) @Override
	public ItemSiniestro findItem(Long id) {
		return itemSiniestroDao.findById(id).orElse(null);
	}

	@Transactional @Override
	public void guardarItem(ItemSiniestro m) {
		itemSiniestroDao.save(m)	;	
	}

}
