package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

/*Data Access Object Interface - This interface defines the 
 * standard operations to be performed on a model object(s).*/

@Component
public class UserDAOService {
	private static List<User> users = new ArrayList<>();
	private static int usersCount = 3;
	
	static {
		users.add(new User(1,"Jax", new Date()));
		users.add(new User(2,"Kat", new Date()));
		users.add(new User(3,"Ronan", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(usersCount+=1);
		}
		users.add(user);
		return user;
	}
	
	public User findUser(int id) {
		for(User user:users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	//delete resource and returning the resource back
	public User deleteUser(int id) {
		Iterator<User> iter = users.iterator();
		while(iter.hasNext()) {
			User user = iter.next();
			if(user.getId()==id) {
				iter.remove();
				return user;
			}
		}
		return null;
	}
}
