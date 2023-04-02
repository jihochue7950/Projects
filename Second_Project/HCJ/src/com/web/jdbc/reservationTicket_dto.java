package com.web.jdbc;

public class reservationTicket_dto {
   private int index;
   private String title;
   private String date;
   private String time;
   private String running_time;
   private int theater;
   private String seat_name;
   private String multiple_seat;
   private String poster;
      

   public String getPoster() {
	return poster;
}

public void setPoster(String poster) {
	this.poster = poster;
}

public String getMultiple_seat() {
	return multiple_seat;
}

public void setMultiple_seat(String multiple_seat) {
	this.multiple_seat = multiple_seat;
}

public reservationTicket_dto(int index, String title, String date, String time, String running_time, int theater,
        String seat_name) {
     super();
     this.index = index;
     this.title = title;
     this.date = date;
     this.time = time;
     this.running_time = running_time;
     this.theater = theater;
     this.seat_name = seat_name;
  }
  
public reservationTicket_dto(String poster,int index, String title, String date, String time, String running_time, int theater,
         String seat_name) {
      super();
      this.poster = poster;
      this.index = index;
      this.title = title;
      this.date = date;
      this.time = time;
      this.running_time = running_time;
      this.theater = theater;
      this.seat_name = seat_name;
   }
   
   public int getIndex() {
      return index;
   }
   public void setIndex(int index) {
      this.index = index;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getDate() {
      return date;
   }
   public void setDate(String date) {
      this.date = date;
   }   
   public String getTime() {
      return time;
   }
   public void setTime(String time) {
      this.time = time;
   }
   public String getRunning_time() {
      return running_time;
   }
   public void setRunning_time(String running_time) {
      this.running_time = running_time;
   }
   public int getTheater() {
      return theater;
   }
   public void setTheater(int theater) {
      this.theater = theater;
   }
   public String getSeat_name() {
      return seat_name;
   }
   public void setSeat_name(String seat_name) {
      this.seat_name = seat_name;
   }
   
}