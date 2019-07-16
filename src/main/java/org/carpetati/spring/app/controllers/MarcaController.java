package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Marca;
import org.carpetati.spring.app.services.IMarcaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/marcas")
@SessionAttributes("marca")
public class MarcaController {

	@Autowired IMarcaServices marcaServices;

	@RequestMapping("/listado")
	public String homePage(Model m, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {		
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Marcas");
		m.addAttribute("lista", marcaServices.findAll(pageRequest));
		return "catalogos/marcas";
	}

	@RequestMapping(value = "/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Marcas.-Formulario de Alta");
		m.addAttribute("marca", new Marca());
		return "catalogos/formmarca";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Marca marca, Model m, BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Marcas.-Formulario de Alta");
			return "/form";
		}

		String msgFlash = (marca.getId() != null) ? "Registro de Marca Editado con Exito"
				: "Nuevo Registro de Marca guardado exitosamente!";
		marcaServices.save(marca);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/marcas/listado";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {		
		if(id>0) {
			Marca marca=marcaServices.findById(id);
			if(marca==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/marcas/listado";
			}else {
				m.addAttribute("titulo", "Marcas.-Formulario de Edicion");
				m.addAttribute("marca", marca);
				return "catalogos/formmarca";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/marcas/listado";
		}	
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id>0) {
			Marca marca= marcaServices.findById(id);
			if(marca==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/marcas/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "El Id "+ id + ", se ha borrado exitosamente");
				marcaServices.delete(marca);
				return "redirect:/marcas/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/marcas/listado";
		}
	}
	
	@RequestMapping(value="/ver/{id}")
	public String ver(@PathVariable Long id, Model m, RedirectAttributes flash ) {		
		Marca marca= marcaServices.findById(id);
		if(marca==null) {
			flash.addFlashAttribute("css", "warning");
			flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
		}		
		m.addAttribute("marca", marca);
		m.addAttribute("titulo","Detalle Marca: "+ marca.getId());
		return "catalogos/vermarca";		
	}
	
}
