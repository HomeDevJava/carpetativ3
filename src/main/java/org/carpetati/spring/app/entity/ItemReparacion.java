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
@Table(name = "reparaciones_items")
@Getter @Setter
public class ItemReparacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//tener cuidado con declarar los campos en la BD, NO declarar como primary/Not Null, ya que marca error
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reparaciones_id")
	private Reparacion reparacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipos_id")
	private Equipo equipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cedis_id")
	private Cedi cedi;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoproblemas_id")
	private TipoProblema tipoproblema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empleados_id")
	private Empleado empleado;

	private String falla;

	private String ro;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecenvio;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecretorno;
}
