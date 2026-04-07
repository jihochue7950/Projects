package com.web.jdbc;

public class nonUser_dto {
   private String non_user_name;
   private String non_user_birth;
   private String non_user_phone_num;
   
   

   public String getNon_user_name() {
      return non_user_name;
   }
   public void setNon_user_name(String non_user_name) {
      this.non_user_name = non_user_name;
   }
   public String getNon_user_birth() {
      return non_user_birth;
   }
   public void setNon_user_birth(String non_user_birth) {
      this.non_user_birth = non_user_birth;
   }
   public String getNon_user_phone_num() {
      return non_user_phone_num;
   }
   public void setNon_user_phone_num(String non_user_phone_num) {
      this.non_user_phone_num = non_user_phone_num;
   }
   public nonUser_dto(String non_user_name, String non_user_birth, String non_user_phone_num) {
      super();
      this.non_user_name = non_user_name;
      this.non_user_birth = non_user_birth;
      this.non_user_phone_num = non_user_phone_num;
   }
   @Override
   public String toString() {
      return "nonUser_dto [non_user_name=" + non_user_name + ", non_user_birth=" + non_user_birth
            + ", non_user_phone_num=" + non_user_phone_num + "]";
   }
   
   
   
}