package org.carpetati.spring.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "siniestros")
@Getter @Setter
public class Siniestros implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha;
	
	private Long ruta;
	
	private String numempleado;
	
	private String nomempleado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cedis_id")
	private Cedi cedis;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tiposiniestros_id")
	private TipoSiniestro tiposiniestro;
	
	


}
