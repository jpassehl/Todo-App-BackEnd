package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.todo.Todo;

@RestController
@CrossOrigin(origins="http://localhost:4200") //tells Spring Boot that we will allow requests from here
public class TodoJpaResource {
	
	/*Once you have an enity in JPA, you can talk to it,save data etc you can use a repository*/
	
	@Autowired
	private TodoJpaRepository todoJpaRepository;

	@GetMapping("/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		//System.out.println("inside getAllTodos");
		//we want to return the todos only for specicifed user
		return todoJpaRepository.findByUsername(username);
		//return todoService.findAll();
	}
	@GetMapping("/jpa/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username,@PathVariable long id){
		//return todoService.findById(id);
		return todoJpaRepository.findById(id).get();
	}

	@DeleteMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteUserTodos(@PathVariable String username, @PathVariable long id) {

		//if sucessful, return no content back
		todoJpaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	//Edit/Update
	@PutMapping("/jpa/users/{username}/todos/{id}")
		public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		
		todo.setUsername(username);
		Todo updatedTodo = todoJpaRepository.save(todo);
		return new ResponseEntity<Todo>(updatedTodo,HttpStatus.OK); //update returns status of OK with conent of updated resource
	}
	//Create a new Todo
	@PostMapping("/jpa/users/{username}/todos")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo){
		
		todo.setUsername(username);
		Todo createdTodo = todoJpaRepository.save(todo);
		System.out.println(createdTodo);
		
		//need to return the location of the created resoruce for post requests
		
		//get current resource url
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		return ResponseEntity.created(uri).build(); //return status of created and returns URI of created resource
	}

}
