package com.moiza.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usergroup")
public class UsergroupEntity {
	@Id // 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션입니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동 생성해주는 어노테이션입니다.
	@Column(name = "usergroup_index")
	private int usergroup_index;
	private int usergroup_user_index;
	private int usergroup_group_index;
	private String usergroup_user_role;
	
//	@ManyToOne(targetEntity = UserEntity.class)
//    @JoinColumn(name = "user_index")
//    private UserEntity UserEntity;
	
	public UsergroupEntity() {}

	@Override
	public String toString() {
		return "UsergroupEntity [usergroup_index=" + usergroup_index + ", usergroup_user_index=" + usergroup_user_index
				+ ", usergroup_group_index=" + usergroup_group_index + ", usergroup_user_role=" + usergroup_user_role
				+ "]";
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
	

}