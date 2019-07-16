package org.carpetati.spring.app.services;

import java.util.List;
import org.carpetati.spring.app.entity.Status;

public interface IStatusServices {
	public List<Status> findAll();

	public void save(Status status);

	public void delete(Long id);

	public void delete(Status status);

	public Status findById(Long id);
}