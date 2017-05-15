package org.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.test.common.util.MD5Util;
import org.test.dao.UserMapper;
import org.test.po.Result;
import org.test.po.User;
import org.test.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Resource
	private UserMapper dao;

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public boolean regist(String name,String username,String password,int age,int gender,String mail,String phonenum) {
		String pwdMd5 = MD5Util.MD5(password);
		
		if(checkUsername(username)){
			User user = new User(name, age, gender, username, pwdMd5, mail, phonenum);
		//	dao.saveUser(user);
			return true;
		}
		return false;
	}

	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
//	public boolean login(String username, String password){
//		Result result;
//		User user = dao.findByName(username);
//		if(user==null){
//			result = new Result(10010,"用户不存在！");
//			return false;
//		}else if(MD5Util.MD5(password).equals(user.getPassword())){
//			return true;
//		}
//			result = new Result(10010,"密码错误！");
//		return false;
//	}
	
	
}
