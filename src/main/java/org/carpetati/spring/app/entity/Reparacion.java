package org.carpetati.spring.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="reparaciones")
@Getter @Setter
public class Reparacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="status_id")
	private Status status;
	
	@Temporal(TemporalType.DATE) @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fectramite;
	
	private String rma;
	
	private String observacion;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="reparaciones_id")
	private List<ItemReparacion> items;
	
	public Reparacion() {
		this.items= new ArrayList<>();
	}
	
	public void AddItemReparacion(ItemReparacion item) {
		this.items.add(item);
	}
	
}
