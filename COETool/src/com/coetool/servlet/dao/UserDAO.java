package com.coetool.servlet.dao;

import com.coetool.servlet.model.User;

public interface UserDAO {

	public int createUser(User user);
	
	public User loginUser(User user);
	
}
