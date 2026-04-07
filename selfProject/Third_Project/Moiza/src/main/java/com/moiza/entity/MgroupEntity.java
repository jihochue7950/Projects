package com.moiza.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mgroup")
public class MgroupEntity {
	@Id // 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션입니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키를 자동 생성해주는 어노테이션입니다.
	@Column(name = "mgroup_index")
	private int mgroup_index;
	private String mgroup_title;
	private int mgroup_img;
	private String mgroup_img_url;
	private String mgroup_introduce;
	private String mgroup_maincategory;
	private String mgroup_middlecategory;
	private String mgroup_local_name;
	private Integer mgroup_minage;
	private Integer mgroup_maxage;
	private String mgroup_gender;
	private Integer mgroup_limit;

	public MgroupEntity() {

	}

	@Override
	public String toString() {
		return "MgroupEntity [mgroup_index=" + mgroup_index + ", mgroup_title=" + mgroup_title + ", mgroup_img="
				+ mgroup_img + ", mgroup_img_url=" + mgroup_img_url + ", mgroup_introduce=" + mgroup_introduce
				+ ", mgroup_maincategory=" + mgroup_maincategory + ", mgroup_middlecategory=" + mgroup_middlecategory
				 + ", mgroup_local_name=" + mgroup_local_name + ", mgroup_minage="
				+ mgroup_minage + ", mgroup_maxage=" + mgroup_maxage + ", mgroup_gender=" + mgroup_gender
				+ ", mgroup_limit=" + mgroup_limit + "]";
	}

	public int getMgroup_index() {
		return mgroup_index;
	}

	public void setMgroup_index(int mgroup_index) {
		this.mgroup_index = mgroup_index;
	}

	public String getMgroup_title() {
		return mgroup_title;
	}

	public void setMgroup_title(String mgroup_title) {
		this.mgroup_title = mgroup_title;
	}

	public int getMgroup_img() {
		return mgroup_img;
	}

	public void setMgroup_img(int mgroup_img) {
		this.mgroup_img = mgroup_img;
	}

	public String getMgroup_img_url() {
		return mgroup_img_url;
	}

	public void setMgroup_img_url(String mgroup_img_url) {
		this.mgroup_img_url = mgroup_img_url;
	}

	public String getMgroup_introduce() {
		return mgroup_introduce;
	}

	public void setMgroup_introduce(String mgroup_introduce) {
		this.mgroup_introduce = mgroup_introduce;
	}

	public String getMgroup_maincategory() {
		return mgroup_maincategory;
	}

	public void setMgroup_maincategory(String mgroup_maincategory) {
		this.mgroup_maincategory = mgroup_maincategory;
	}

	public String getMgroup_middlecategory() {
		return mgroup_middlecategory;
	}

	public void setMgroup_middlecategory(String mgroup_middlecategory) {
		this.mgroup_middlecategory = mgroup_middlecategory;
	}



	public String getMgroup_local_name() {
		return mgroup_local_name;
	}

	public void setMgroup_local_name(String mgroup_local_name) {
		this.mgroup_local_name = mgroup_local_name;
	}

	public Integer getMgroup_minage() {
		return mgroup_minage;
	}

	public void setMgroup_minage(Integer mgroup_minage) {
		this.mgroup_minage = mgroup_minage;
	}

	public Integer getMgroup_maxage() {
		return mgroup_maxage;
	}

	public void setMgroup_maxage(Integer mgroup_maxage) {
		this.mgroup_maxage = mgroup_maxage;
	}

	public String getMgroup_gender() {
		return mgroup_gender;
	}

	public void setMgroup_gender(String mgroup_gender) {
		this.mgroup_gender = mgroup_gender;
	}

	public Integer getMgroup_limit() {
		return mgroup_limit;
	}

	public void setMgroup_limit(Integer mgroup_limit) {
		this.mgroup_limit = mgroup_limit;
	}


}