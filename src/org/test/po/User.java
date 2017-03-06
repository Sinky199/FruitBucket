package org.test.po;

public class User {
	private Long id;
	private String name;
	private int age;
	private int gender;
	private String username;
	private String password;
	private String mail;
	private String phonenum;
	
	
	
	
	public User() {
		super();
	}
	public User(String name, int age, int gender, String username,
			String password, String mail, String phonenum) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.phonenum = phonenum;
	}
	public User(Long id, String name, int age, int gender, String username,
			String password, String mail, String phonenum) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.phonenum = phonenum;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}
