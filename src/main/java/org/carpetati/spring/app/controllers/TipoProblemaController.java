package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.TipoProblema;
import org.carpetati.spring.app.services.ITipoProblemaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("tiposproblemas")
@SessionAttributes("tipoProblema")
public class TipoProblemaController {
	
	@Autowired ITipoProblemaServices tipoProblemaServices;
	
	@RequestMapping(value = "/listado")
	public String homePage(Model m) {
		m.addAttribute("titulo", "Catalogo de TipoProblema");
		m.addAttribute("lista", tipoProblemaServices.findAll());
		return "catalogos/tipoproblemas";
	}
	
	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Tipo de Problema.- Formulario de Alta");
		m.addAttribute("tipoProblema", new TipoProblema());
		return "catalogos/tipoproblemaform";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(TipoProblema tipoProblema, Model m, BindingResult result, RedirectAttributes flash, SessionStatus state) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "TipoProblema.- Fromulario de Alta");
			return "catalogos/tipoproblemaform";
		}

		String msgFlash = (tipoProblema.getId() != null) ? "Registro de TipoProblema Editado con Exito"
				: "Nuevo Registro de TipoProblema guardado exitosamente!";
		tipoProblemaServices.save(tipoProblema);
		state.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/tiposproblemas/listado";
	}
	
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if (id > 0) {
			TipoProblema tipoProblema = tipoProblemaServices.findById(id);
			if (tipoProblema == null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/tipoproblemas/listado";
			} else {
				m.addAttribute("titulo", "TipoProblema.-Formulario de Edición");
				m.addAttribute("tipoProblema", tipoProblema);
				return "catalogos/tipoproblemaform";
			}
		} else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/tipoproblemas/listado";
		}

	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			TipoProblema TipoProblema= tipoProblemaServices.findById(id);
			if(TipoProblema==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/tipoproblemas/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				tipoProblemaServices.delete(TipoProblema);
				return "redirect:/tipoproblemas/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/tipoproblemas/listado";
		}
	}

}
