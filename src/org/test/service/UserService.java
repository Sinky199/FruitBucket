package org.test.service;

import java.util.List;

import org.test.po.User;

public interface UserService {
	List<User> findAllUsers();
	boolean regist(String name,String username,String password,int age,int gender,String mail,String phonenumS);
	boolean login(String username, String password);
	public boolean checkUsername(String username);
}
