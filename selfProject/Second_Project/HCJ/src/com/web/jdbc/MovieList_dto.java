package com.web.jdbc;

public class MovieList_dto {
      private int movie_num;
      private String title;
      private String genre;
      private int age_limit;
      private String running_time;
      private String poster;  
      private String trailer_url;
      

   public MovieList_dto(int movie_num, String title, String genre, int age_limit, String running_time, String poster,
         String trailer_url) {
      super();
      this.movie_num = movie_num;
      this.title = title;
      this.genre = genre;
      this.age_limit = age_limit;
      this.running_time = running_time;
      this.poster = poster;
      this.trailer_url = trailer_url;
   }
   public int getMovie_num() {
      return movie_num;
   }
   public void setMovie_num(int movie_num) {
      this.movie_num = movie_num;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getGenre() {
      return genre;
   }
   public void setGenre(String genre) {
      this.genre = genre;
   }
   public int getAge_limit() {
      return age_limit;
   }
   public void setAge_limit(int age_limit) {
      this.age_limit = age_limit;
   }
   public String getRunning_time() {
      return running_time;
   }
   public void setRunning_time(String running_time) {
      this.running_time = running_time;
   }
   public String getPoster() {
      return poster;
   }
   public void setPoster(String poster) {
      this.poster = poster;
   }   
   public String getTrailer_url() {
      return trailer_url;
   }
   public void setTrailer_url(String trailer_url) {
      this.trailer_url = trailer_url;
   }
   @Override
   public String toString() {
      return "MovieList_dto [movie_num=" + movie_num + ", title=" + title + ", genre=" + genre + ", age_limit="
            + age_limit + ", running_time=" + running_time + ", poster=" + poster + ", trailer_url=" + trailer_url
            + "]";
   }

}