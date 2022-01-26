package com.nrifintech.medico.request;

public class PatientRegisterRequest {
	private String name;
	private String username;
	private String phone_no;
	private String password;
	private String confirm_password;
	
	
	
	public PatientRegisterRequest(String name, String username, String phone_no, String password,
			String confirm_password) {
		this.name = name;
		this.username = username;
		this.phone_no = phone_no;
		this.password = password;
		this.confirm_password = confirm_password;
	}
	@Override
	public String toString() {
		return "PatientRegisterRequest [name=" + name + ", username=" + username + ", phone_no=" + phone_no
				+ ", password=" + password + ", confirm_password=" + confirm_password + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm_password() {
		return confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

}
