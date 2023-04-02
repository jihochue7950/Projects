package com.web.jdbc;

import java.sql.SQLException;

public class movie_dto {
   
   private int theater;
   private String date;
   private int movie_num;
   private String time;
   private int sch_num;
   
   public movie_dto(int sch_num,int theater,String date, String time) {
      super();
      this.sch_num = sch_num;
      this.theater = theater;
      this.date = date;
      this.time = time;
   }
   public movie_dto(int theater,String date, String time) {
         super();
         this.theater = theater;
         this.date = date;
         this.time = time;
      }
   
   public int getSch_num() {
      return sch_num;
   }
   public void setSch_num(int sch_num) {
      this.sch_num = sch_num;
   }

   public int getTheater() {
      return theater;
   }
   public void setTheater(int theater) {
      this.theater = theater;
   }
   public String getDate() {
      return date;
   }
   public void setDate(String date) {
      this.date = date;
   }
   public int getMovie_num() {
      return movie_num;
   }
   public void setMovie_num(int movie_num) {
      this.movie_num = movie_num;
   }
   public String getTime() {
      return time;
   }
   public void setTime(String time) {
      this.time = time;
   }
   @Override
   public String toString() {
      return "movie_dto [theater=" + theater + ", date=" + date + ", time=" + time + ", sch_num=" + sch_num + "]";
   }

}