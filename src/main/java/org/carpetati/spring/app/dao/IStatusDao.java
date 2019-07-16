package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusDao extends JpaRepository<Status, Long> {

}
