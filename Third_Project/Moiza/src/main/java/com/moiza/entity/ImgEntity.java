
package com.moiza.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "img")
public class ImgEntity  {
   
	@Id // 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션입니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동 생성해주는 어노테이션입니다.
	@Column(name = "img_index")
	private int img_index;
	private String img_url;
	
	public ImgEntity() {}
	
	@Override
	public String toString() {
		return "ImgEntity [img_index=" + img_index + ", img_url=" + img_url + "]";
	}

	public int getImg_index() {
		return img_index;
	}

	public void setImg_index(int img_index) {
		this.img_index = img_index;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
}