package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Empleado;
import org.carpetati.spring.app.services.ICediServices;
import org.carpetati.spring.app.services.IEmpleadoServices;
import org.carpetati.spring.app.services.IPuestoServices;
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
@RequestMapping("/empleados")
@SessionAttributes("empleado")
public class EmpleadoController {

	@Autowired IEmpleadoServices empleadoServices;
	@Autowired ICediServices cediServices;
	@Autowired IPuestoServices puestoServices;

	@RequestMapping("/listado")
	public String homePage(Model m, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Empleados");
		m.addAttribute("lista", empleadoServices.findAll(pageRequest));
		return "catalogos/empleados";
	}

	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Empleados.- Formulario de Alta");
		m.addAttribute("listapuestos", puestoServices.findAll());
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("empleado", new Empleado());
		return "catalogos/formempleado";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Model m, Empleado empleado, BindingResult result, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Empleados.- Formulario de Alta");
			m.addAttribute("listapuestos", puestoServices.findAll());
			m.addAttribute("listacedis", cediServices.findAll());
			return "catalogos/formempleado";
		}

		String msgFlash = (empleado.getId() != null) ? "Registro de Modelo Editado con Exito"
				: "Nuevo Registro de Empleado guardado exitosamente!";
		
		empleadoServices.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/empleados/listado";
	}

	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Empleado empleado= empleadoServices.findById(id);
			if(empleado==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/empleados/listado";
			}else {
				m.addAttribute("titulo", "Empleados.- Formulario de Alta");
				m.addAttribute("listapuestos", puestoServices.findAll());
				m.addAttribute("listacedis", cediServices.findAll());
				m.addAttribute("empleado", empleado);
				return "catalogos/formempleado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/empleados/listado";
		}		
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Empleado empleado= empleadoServices.findById(id);
			if(empleado==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id " + id + " no existe en el catalogo");
				return "redirect:/empleados/listado";
			}else {
				empleadoServices.delete(empleado);
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				return "redirect:/empleados/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/empleados/listado";
		}
		
	}
}
