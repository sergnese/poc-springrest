package com.ds.poc.springrest;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This controller does a CRUD of Entities Computer. It's an example of how implement Rest methods 
 * GET, POST, PUT, DELETE with Spring MVC.
 * Entities computer are store in a pseudo persistance unit.
 * You can  use RestClient, a firefox's plugin to test the Rest application : https://addons.mozilla.org/en-US/firefox/addon/restclient/ .
 * - Start by testing a GET on http://localhost:8080/spring-rest/rest/computer .
 * - Test POST http://localhost:8080/spring-rest/rest/computer
 * 		Before sending the request, add an http header Content-Type with the button Add Request Header of Rest client : Content-Type:application/json; charset=utf-8 add a JSON content in the request body
 *		  {"reference":"MAC","description":"Test","model":"MB Pro","brand":"Apple"}
 * - Do a second POST
 * - Delete a computer with a DELETE on http://localhost:8080/spring-rest/rest/computer/2
 * - Update a computer with a PUT on http://localhost:8080/spring-rest/rest/computer/1 with Content-Type:application/json; charset=utf-8 add a JSON content in the request body {"reference":"MACR","description":"Test","model":"Macbook Air","brand":"Apple"}
 *  
 */
@Controller
@RequestMapping("/computer") 
public class ComputerController {
	
	/*pseudo persistance unit uses by the controller*/
	private static final ComputerStorage computerStorage = new ComputerStorage();
	
	@RequestMapping(method = RequestMethod.GET) 
	public @ResponseBody List<Computer> getComputers() { 
		return computerStorage.getAll();
	}
	
	@RequestMapping(method = RequestMethod.POST) 
	@ResponseBody 
	public void addComputer(@RequestBody Computer computer) { 
		computerStorage.add(computer);
	}
	
	@RequestMapping(value="{id}",method = RequestMethod.PUT)
	@ResponseBody
	public void putComputer(@PathVariable long id, @RequestBody Computer computer) {
		computer.setId(id);
		computerStorage.update(computer);
	}	

	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteComputer(@PathVariable long id) {
		computerStorage.delete(id);
	}

	
}