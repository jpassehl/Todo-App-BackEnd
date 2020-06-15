package com.in28minutes.rest.webservices.restfulwebservices.user;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//This is a controller

@RestController
public class UserResource {
	
	@Autowired
	private UserDAOService service;

	//GET /users
	//retrieveAllUsers
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	//GET /users/{id}
	//retireveOneUser
	
	@GetMapping("/users/{id}")
	public EntityModel retrieveOneUser(@PathVariable int id) {
		User user = service.findUser(id);
		
		if(user == null) {
			throw new UserNotFoundException("id- "+ id);
		}
		
		EntityModel<User> model = new EntityModel<>(user);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		
		
		return model;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteUser(id);
		
		if(user == null) {
			throw new UserNotFoundException("id- "+ id);
		}
		//if nothing is returned it means it is a sucess
		
	}
	
	//input -details of user
	//output - Status: CREATED & Return the created URI
	
	@PostMapping("/users")
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		//we want to append the user's id to the URI
		
		URI locationCreated = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(locationCreated).build();
	}
	
}
