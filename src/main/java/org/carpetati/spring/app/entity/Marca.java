package org.carpetati.spring.app.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="marcas")
@Getter @Setter
public class Marca implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@OneToMany(mappedBy="marca",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonBackReference
	//@JsonManagedReference
	private List<Modelo> modelo;
	
}