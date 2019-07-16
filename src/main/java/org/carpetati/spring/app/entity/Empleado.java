package org.carpetati.spring.app.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="empleados")
@Getter @Setter
public class Empleado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String numempleado;
	
	private String nombre;
	
	private String apellidos;
	
	private String email;
	
	private String telefono;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cedis_id")
	private Cedi cedi;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="puestos_id")
	private Puesto puesto;

}
