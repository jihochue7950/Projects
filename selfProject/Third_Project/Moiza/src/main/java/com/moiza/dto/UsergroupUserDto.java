package com.moiza.dto;

public class UsergroupUserDto {
	private int usergroup_index;
	private int usergroup_user_index;
	private int usergroup_group_index;
	private String usergroup_user_role;
	private int user_index;
	private String user_name;
	private String user_phone;
	private String user_birth;
	private String user_gender;
	private String username;
	private String password;
	private String user_joinday;
	private Integer user_out;
	private Integer enabled;
	private String authority;
	
	@Override
	public String toString() {
		return "UsergroupUserDto [usergroup_index=" + usergroup_index + ", usergroup_user_index=" + usergroup_user_index
				+ ", usergroup_group_index=" + usergroup_group_index + ", usergroup_user_role=" + usergroup_user_role
				+ ", user_index=" + user_index + ", user_name=" + user_name + ", user_phone=" + user_phone
				+ ", user_birth=" + user_birth + ", user_gender=" + user_gender + ", username=" + username
				+ ", password=" + password + ", user_joinday=" + user_joinday + ", user_out=" + user_out + ", enabled="
				+ enabled + ", authority=" + authority + "]";
	}

	public int getUsergroup_index() {
		return usergroup_index;
	}

	public void setUsergroup_index(int usergroup_index) {
		this.usergroup_index = usergroup_index;
	}

	public int getUsergroup_user_index() {
		return usergroup_user_index;
	}

	public void setUsergroup_user_index(int usergroup_user_index) {
		this.usergroup_user_index = usergroup_user_index;
	}

	public int getUsergroup_group_index() {
		return usergroup_group_index;
	}

	public void setUsergroup_group_index(int usergroup_group_index) {
		this.usergroup_group_index = usergroup_group_index;
	}

	public String getUsergroup_user_role() {
		return usergroup_user_role;
	}

	public void setUsergroup_user_role(String usergroup_user_role) {
		this.usergroup_user_role = usergroup_user_role;
	}

	public int getUser_index() {
		return user_index;
	}

	public void setUser_index(int user_index) {
		this.user_index = user_index;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}

	public String getUser_gender() {
		return user_gender;
	}

	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
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

	public String getUser_joinday() {
		return user_joinday;
	}

	public void setUser_joinday(String user_joinday) {
		this.user_joinday = user_joinday;
	}

	public Integer getUser_out() {
		return user_out;
	}

	public void setUser_out(Integer user_out) {
		this.user_out = user_out;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
	
	
}
