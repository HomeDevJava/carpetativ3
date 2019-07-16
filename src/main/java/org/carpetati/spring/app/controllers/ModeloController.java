package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Modelo;
import org.carpetati.spring.app.services.IDeviceServices;
import org.carpetati.spring.app.services.IMarcaServices;
import org.carpetati.spring.app.services.IModeloServices;
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
@SessionAttributes("modelo")
@RequestMapping("/modelos")
public class ModeloController {

	@Autowired private IModeloServices modeloServices;
	@Autowired private IMarcaServices marcaServices;
	@Autowired private IDeviceServices deviceServices;
	
	@RequestMapping(value="/listado")
	public String homepage(Model m,@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Modelos");
		m.addAttribute("lista", modeloServices.findAll(pageRequest));
		return "catalogos/modelos";
	}
	
	@RequestMapping(value="/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Modelos.- Formulario de Alta");
		m.addAttribute("listamarcas", marcaServices.findAll());
		m.addAttribute("listadevices", deviceServices.findAll());
		m.addAttribute("modelo", new Modelo());
		return "catalogos/formmodelo";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(Modelo modelo,Model m,BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			m.addAttribute("titulo", "Modelos.-Formulario de Alta");
			return "catalogos/formmodelo";
		}
		
		String msgFlash = (modelo.getId() != null) ? "Registro de Modelo Editado con Exito"
				: "Nuevo Registro de Modelo guardado exitosamente!";
		modeloServices.save(modelo);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/modelos/listado";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Modelo modelo= modeloServices.findById(id);
			if(modelo==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/modelos/listado";
			}else {
				m.addAttribute("titulo", "Modelos.- Formulario de Edición");
				m.addAttribute("listamarcas", marcaServices.findAll());
				m.addAttribute("listadevices", deviceServices.findAll());
				m.addAttribute("modelo", modelo);
				return "catalogos/formmodelo";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/modelos/listado";
		}	
	}
	
	@RequestMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Modelo modelo= modeloServices.findById(id);
			if(modelo==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/modelos/listado";
			}else {
				m.addAttribute("titulo", "Modelos.- Vista previa");
				m.addAttribute("modelo", modelo);
				return "catalogos/verequipo";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/modelos/listado";
		}	
	}
}