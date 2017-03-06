package org.test.dao;

import java.util.List;

import org.test.po.User;

public interface UserMapper {
	List<User> findAllUsers();
	void saveUser(User user);
	User findByName(String name);
}
