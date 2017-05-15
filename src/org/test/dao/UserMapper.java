package org.test.dao;

import java.util.List;

import org.test.po.User;
import org.test.po.UserInfo;

public interface UserMapper {
	List<User> findAllUsers();
	void saveUser(UserInfo user);
	UserInfo findByName(String name);
}
