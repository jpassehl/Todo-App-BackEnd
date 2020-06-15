package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//The enitiy the repository is managing are Todos, and the primary key is a Long (<Todo,Long>)

@Repository
public interface TodoJpaRepository extends JpaRepository <Todo,Long> {
	
	//return a list of Todos associated with the username
	List<Todo>findByUsername(String username);
}
