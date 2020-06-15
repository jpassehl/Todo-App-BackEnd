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
public class TodoResource {

	//create a service
	@Autowired
	private TodoHardcodedService todoService;

	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		System.out.println("inside getAllTodos");
		return todoService.findAll();
	}
	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username,@PathVariable long id){
		//System.out.println("inside getAllTodos");
		return todoService.findById(id);
	}

	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteUserTodos(@PathVariable String username, @PathVariable long id) {
		System.out.println("inside deleteUserTodos");
		Todo todo  = todoService.deleteById(id);
		//If the todo was successfully deleted we should get a todo object back - so return status of no Content
		//System.out.println("Todo" + todo);
		if(todo!=null){
			return ResponseEntity.noContent().build(); //typically returns no content when sucessful
		}//otherwise return status of not found.
		return ResponseEntity.notFound().build(); //upon fail return not found

	}
	//Edit/Update
	@PutMapping("/users/{username}/todos/{id}")
		public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		Todo updatedTodo = todoService.save(todo);
		return new ResponseEntity<Todo>(updatedTodo,HttpStatus.OK); //update returns status of OK with conent of updated resource
	}
	//Create a new Todo
	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo){
		
		//same method is called
		Todo createdTodo = todoService.save(todo);
		System.out.println(createdTodo);
		
		//need to return the location of the created resoruce for post requests
		
		//get current resource url
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		return ResponseEntity.created(uri).build(); //return status of created and returns URI of created resource
	}

}
