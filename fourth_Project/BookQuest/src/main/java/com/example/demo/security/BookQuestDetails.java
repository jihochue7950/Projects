package com.example.demo.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.entity.User;



public class BookQuestDetails implements UserDetails {
   
   
   private User user;

   public BookQuestDetails(User user) {
       this.user = user;
    }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
         String roles = user.getRole();
         
         List<SimpleGrantedAuthority> authories = new ArrayList<>();
         
         authories.add(new SimpleGrantedAuthority(roles));
    
         return authories;
   }
   
   @Override
   public String getPassword() {
       return user.getPassword();
   }

   @Override
   public String getUsername() {
       return user.getEmail();
   }
   
   public int getUserid() {
	   return user.getUserId();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return user.isEnabled();
   }

}