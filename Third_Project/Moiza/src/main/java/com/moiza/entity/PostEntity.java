package com.moiza.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class PostEntity {

	@Id // 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션입니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동 생성해주는 어노테이션입니다.
	@Column(name = "post_index")
	private int post_index;	
	private int post_usergroup_index;
	private String post_maintext;
	private String post_date;
	private String post_time;
	private Integer post_like;
	private Integer post_view;
	
	public PostEntity() {

	}
	
	@Override
	public String toString() {
		return "PostEntity [post_index=" + post_index + ", post_usergroup_index=" + post_usergroup_index
				+ ", post_maintext=" + post_maintext + ", post_date=" + post_date + ", post_time=" + post_time
				+ ", post_like=" + post_like + ", post_view=" + post_view + "]";
	}

	public int getPost_index() {
		return post_index;
	}

	public void setPost_index(int post_index) {
		this.post_index = post_index;
	}

	public int getPost_usergroup_index() {
		return post_usergroup_index;
	}

	public void setPost_usergroup_index(int post_usergroup_index) {
		this.post_usergroup_index = post_usergroup_index;
	}

	public String getPost_maintext() {
		return post_maintext;
	}

	public void setPost_maintext(String post_maintext) {
		this.post_maintext = post_maintext;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}

	public Integer getPost_like() {
		return post_like;
	}

	public void setPost_like(Integer post_like) {
		this.post_like = post_like;
	}

	public Integer getPost_view() {
		return post_view;
	}

	public void setPost_view(Integer post_view) {
		this.post_view = post_view;
	}


}