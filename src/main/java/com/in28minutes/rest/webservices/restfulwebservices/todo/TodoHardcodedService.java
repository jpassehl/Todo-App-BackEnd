package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;


@Service //we want Spring to manage this
public class TodoHardcodedService {
	
	//static list of Todos- for now this will act as our database
	private static List<Todo> todos = new ArrayList();
	private static Long idCounter = 0L;
	
	static {
		todos.add(new Todo(++idCounter,"Jacqueline",
				"Learn Full Stack Web Development (HARD CODED)", new Date(),false));
		
		todos.add(new Todo(++idCounter,"Jacqueline",
				"Learn REST API (HARD CODED)", new Date(),false));
		
		todos.add(new Todo(++idCounter,"Jacqueline",
				"Become a Software Engineer (HARD CODED)", new Date(),false));
	}
	
	public List<Todo> findAll(){
		return todos;
	}
	
	public Todo deleteById(long id) {
		Todo todo = findById(id);
		if(todo != null) {
			todos.remove(todo);
		}
		System.out.println(todo);
		return todo;
	}
	
	public Todo findById(long id) {
		for(Todo todo : todos) {
			if(todo.getId() == id) {
				return todo;
			}
		}//return null if not found
		return null;
	}
	public Todo save(Todo todo) {
		if(todo.getId()==-1 || todo.getId() == 0 ) { //if the request is to create a new ID
			todo.setId(++idCounter);
			todos.add(todo);
		}else {
			deleteById(todo.getId());
			todos.add(todo);
		}
		return todo;
	}
}
