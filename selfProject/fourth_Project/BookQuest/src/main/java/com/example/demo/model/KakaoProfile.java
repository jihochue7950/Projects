package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoProfile {

	private Long id;
	private String connected_at;
	private Properties properties;
	private KakaoAccount kakao_account;

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Properties {

		private String nickname;

		public Properties() {}
		
		@Override
		public String toString() {
			return "Properties [nickname=" + nickname + "]";
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class KakaoAccount {

		private Boolean profile_nickname_needs_agreement;
		private Profile profile;
		private Boolean has_email;
		private Boolean email_needs_agreement;
		
		@JsonIgnoreProperties(ignoreUnknown=true)
		public class Profile {

			private String nickname;

			public Profile() {}
			
			@Override
			public String toString() {
				return "Profile [nickname=" + nickname + "]";
			}

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}

		}

		public KakaoAccount() {}

		@Override
		public String toString() {
			return "KakaoAccount [profile_nickname_needs_agreement=" + profile_nickname_needs_agreement + ", profile="
					+ profile + ", has_email=" + has_email + ", email_needs_agreement=" + email_needs_agreement + "]";
		}

		public Boolean getProfile_nickname_needs_agreement() {
			return profile_nickname_needs_agreement;
		}

		public void setProfile_nickname_needs_agreement(Boolean profile_nickname_needs_agreement) {
			this.profile_nickname_needs_agreement = profile_nickname_needs_agreement;
		}

		public Profile getProfile() {
			return profile;
		}

		public void setProfile(Profile profile) {
			this.profile = profile;
		}

		public Boolean getHas_email() {
			return has_email;
		}

		public void setHas_email(Boolean has_email) {
			this.has_email = has_email;
		}

		public Boolean getEmail_needs_agreement() {
			return email_needs_agreement;
		}

		public void setEmail_needs_agreement(Boolean email_needs_agreement) {
			this.email_needs_agreement = email_needs_agreement;
		}

	}
	
	
	public KakaoProfile() {}

	@Override
	public String toString() {
		return "KakaoProfile [id=" + id + ", connected_at=" + connected_at + ", properties=" + properties
				+ ", kakao_account=" + kakao_account + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConnected_at() {
		return connected_at;
	}

	public void setConnected_at(String connected_at) {
		this.connected_at = connected_at;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public KakaoAccount getKakao_account() {
		return kakao_account;
	}

	public void setKakao_account(KakaoAccount kakao_account) {
		this.kakao_account = kakao_account;
	}

	
}
