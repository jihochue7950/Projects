package com.moiza.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity

@Table(name = "authorities")
public class Authorities  {
   
   @Id // 데이터베이스 테이블의 기본 키(PK)와 객체의 필드를 매핑시켜주는 어노테이션입니다.
   //@GeneratedValue(strategy = GenerationType.AUTO) // 기본 키를 자동 생성해주는 어노테이션입니다.
   @GeneratedValue(generator="USER_GENERATOR")
   @GenericGenerator(name="USER_GENERATOR",strategy="uuid")
   @Column(name = "username")
   private String username;
   private String authority;
   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }
   public String getAuthority() {
      return authority;
   }
   public void setAuthority(String authority) {
      this.authority = authority;
   }
   @Override
   public String toString() {
      return "AuthoritiesEntity [username=" + username + ", authority=" + authority + "]";
   }

}