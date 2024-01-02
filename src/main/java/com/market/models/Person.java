package com.market.models;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.AssertTrue;

public class Person {
	@Id
	private String id;
	
	String firstName;
	String code;
	String secondName;
	String email;
	String password;
	String confirmPassword;
	int phone;
	Date registerDate;
	
	public Person(String id, String firstName, String secondName,String code, String email, int phone, Date registerDate, String password, String confirmPassword) {
		super();
		
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.phone = phone;
		this.code = code;
		this.registerDate = registerDate;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@AssertTrue(message = "A senha e a confirmação de senha devem ser iguais")
    private boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
