package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Device;
import org.carpetati.spring.app.services.IDeviceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("device")
@RequestMapping("/devices")
public class DeviceController {

	@Autowired private IDeviceServices deviceServices;
		
	@GetMapping("/listado")
	public String list(Model m, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de dispositivos");
		m.addAttribute("page", deviceServices.findAll(pageRequest));
		return "catalogos/devices2";
	}
	
	@RequestMapping(value="/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Dispositivos.- Formulario de Alta");
		m.addAttribute("device", new Device());
		return "catalogos/formdevice";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(Device device,Model m, BindingResult  result, RedirectAttributes flash, SessionStatus status){
		if(result.hasErrors()) {
			m.addAttribute("titulo", "Dispositivos.- Formulario de Alta");
			return "catalogos/formdevice";
		}
		String msgFlash=(device.getId()!=null)? "Registro de Devices Actualizado existosamente!" : "Nuevo Registro Device Guardado";
		deviceServices.save(device);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/devices/listado";
	}
		
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Device device=deviceServices.findById(id);
			if(device==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/devices/listado";
			}else {
				m.addAttribute("titulo", "Devices.-Formulario de Edición");
				m.addAttribute("device", device);
				return "catalogos/formdevice";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/devices/listado";
		}
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Device device= deviceServices.findById(id);
			if(device==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/devices/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				deviceServices.delete(device);
				return "redirect:/devices/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/devices/listado";
		}
	}
}
