package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="branches")
public class Branches {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="branches_id")
	private Integer branchId;
	
	@Column(name="branches_name", length = 32, nullable = false)
	private String branchName;

	@Column(name="branches_address",length = 64, nullable = false)
	private String branchAddress;
	//위도, 남북
	@Column(length = 64)
	private Double latitude;
	//경도, 동서, 그리니치 천문대
	@Column(length = 64)
	private Double longitude;
	
	public Branches() {
	}

	public Branches(Integer branchId) {
		this.branchId = branchId;
	}

	public Branches(String branchName, String branchAddress, Double latitude, Double longitude) {
		this.branchName = branchName;
		this.branchAddress = branchAddress;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Branch [branchId=" + branchId + ", branchName=" + branchName + ", branchAddress=" + branchAddress
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
}
