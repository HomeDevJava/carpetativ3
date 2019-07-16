package org.carpetati.spring.app.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelos")
@Getter @Setter
public class Modelo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marcas_id")
	// @JsonBackReference
	@JsonManagedReference
	private Marca marca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "devices_id", referencedColumnName = "id")
	@JsonManagedReference
	//@JsonBackReference
	private Device device;

	@OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JsonManagedReference
	@JsonBackReference
	private List<Equipo> equipo;

}