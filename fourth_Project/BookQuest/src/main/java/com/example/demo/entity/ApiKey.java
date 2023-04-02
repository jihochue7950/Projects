package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "api_key")
public class ApiKey {

	@Id
	@Column(name = "api_key_name", nullable = false)
	private String apiKeyName;

	@Column(name = "api_key_value")
	private String apiKeyValue;

	public ApiKey() {
	}

	public ApiKey(String apiKeyName, String apiKeyValue) {
		this.apiKeyName = apiKeyName;
		this.apiKeyValue = apiKeyValue;
	}
	
	@Override
	public String toString() {
		return "ApiKey [apiKeyName=" + apiKeyName + ", apiKeyValue=" + apiKeyValue + "]";
	}

	public String getApiKeyName() {
		return apiKeyName;
	}

	public void setApiKeyName(String apiKeyName) {
		this.apiKeyName = apiKeyName;
	}

	public String getApiKeyValue() {
		return apiKeyValue;
	}

	public void setApiKeyValue(String apiKeyValue) {
		this.apiKeyValue = apiKeyValue;
	}
	
	
	
	

}
