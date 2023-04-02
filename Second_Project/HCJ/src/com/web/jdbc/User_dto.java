package com.web.jdbc;

public class User_dto{
   
   private int user_index;
   private String id;
   private String name;
   private String birth;
   private String phone_num;
   private String password;
   
   
   
 
   
   
   public int getUser_index() {
      return user_index;
   }
   public void setUser_index(int user_index) {
      this.user_index = user_index;
   }

   public String getId() {
   return id;
}
public void setId(String id) {
   this.id = id;
}
public String getPassword() {
   return password;
}
public void setPassword(String password) {
   this.password = password;
}
public User_dto(String id, String phone_num) {
      super();
      this.id = id;
      this.phone_num = phone_num;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getBirth() {
      return birth;
   }
   public void setBirth(String birth) {
      this.birth = birth;
   }
   public String getPhone_num() {
      return phone_num;
   }
   public void setPhone_num(String phone_num) {
      this.phone_num = phone_num;
   }
   public User_dto(int user_index,String id,String name,String birth,String phone_num) {
      this.user_index = user_index;
      this.id = id;
      this.name = name;
      this.birth = birth;
      this.phone_num = phone_num;
   }
}