package com.web.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.sql.DataSource;

public class hcj_model {
	
private DataSource dataSource;

	public hcj_model(DataSource theDataSource){
		dataSource = theDataSource;
	}
	
		public boolean Login (String id , String password) throws SQLException, ServletException, IOException {
        Connection conn;
        Statement mySt;
        ResultSet myRs;
		
		
		  conn = dataSource.getConnection();
		  mySt = conn.createStatement();
		  myRs = mySt.executeQuery("select id , password from user");
		  
		  HashMap<String,String> IDPWD = new HashMap<>();
		  // HashMap으로 데이터베이스에서 id 와 password를 불러와 일치여부 확인
		  while(myRs.next()) {
		     String id_ = myRs.getString("id"); 
		     String pwd_ = myRs.getString("password");
		     IDPWD.put(id_, pwd_);
		  }
		  boolean a = (IDPWD.containsKey(id) && IDPWD.get(id).equals(password));   
		  return a;

			// 리턴 값으로 true 나 false를 반환 . 서블릿 단에서 호출 할 예정
			// end login()
	}


		public void cancel(int index, String multiple_seat) throws Exception{
		      Connection conn = null;
		      PreparedStatement pst = null;      
		      
		      try {
		         conn = dataSource.getConnection();
		         String sql = null;
		            if(multiple_seat == null) {
		               sql = "UPDATE reservation SET cancellations = \"cancel\" "
		                     + "where reservation.index = ?";
		               pst = conn.prepareStatement(sql);
		               pst.setInt(1, index);
		            }else if(multiple_seat != null) {
		            	//시간을 기반으로 한 multiple_seat column에 데이터 값이 있으면
		               sql = "UPDATE reservation SET cancellations = \"cancel\" "
		                     +" where multiple_seat= ?";
		               //일치하는 multiple_seat row(cancellations)에 cancel값으로 업데이트 
		               pst = conn.prepareStatement(sql);
		               pst.setString(1, multiple_seat);
		            }
		         pst.execute();
		         
		      }finally {
		         close(conn, pst, null);
		      }
		      
		   }
		      
		      
		   public void delete(int index, String multiple_seat) throws Exception{
		      Connection conn = null;
		      PreparedStatement pst = null;
		      
		      try {
		         conn = dataSource.getConnection();
		         String sql  = null;
		         if(multiple_seat == null) {            
		            sql = "Delete from reservation where reservation.index = ?";
		            pst = conn.prepareStatement(sql);
		            pst.setInt(1, index);
		         }else if(multiple_seat != null){
		        	//시간을 기반으로 한 multiple_seat column에 데이터 값이 있으면
		            sql = "Delete from reservation where multiple_seat= ?";
		          //일치하는 multiple_seat row(cancellations)를 delete
		            pst = conn.prepareStatement(sql);
		            pst.setString(1, multiple_seat);
		         }
		         pst.execute();
		      }finally {
		         close(conn, pst, null);
		      }
		      
		   }
		   public String indexToMultipleseat(int index) throws Exception{
		         Connection conn = null;
		         PreparedStatement mySt = null;
		         ResultSet myRs = null;
		         
		         try {
		            conn = dataSource.getConnection();
		            String sql = "SELECT multiple_seat "
		                  + "FROM reservation "
		                  + "where reservation.index=?";
		            mySt = conn.prepareStatement(sql);
		            mySt.setInt(1, index);
		            
		            myRs = mySt.executeQuery();
		            myRs.next();
		         String multiple_seat = myRs.getString("multiple_seat");
		         return multiple_seat;
		         
		         }finally {
		            close(conn, mySt, null);
		         }
		      }
	public int sessionidChangeUserTable(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet myRs = null;			

		try{
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM user WHERE id = ?";

			pst= conn.prepareStatement(sql);
			pst.setString(1, id);
			myRs = pst.executeQuery();
			myRs.next();
			
			int user_index = myRs.getInt("user_index");
			return user_index;
	
			
		}finally {
			close(conn, pst, myRs);
		}
	}
	
	   public List<MovieList_dto> getMovieList() throws Exception{
		      List<MovieList_dto> Movies = new ArrayList<>();
		      Connection conn = null;
		      Statement mySt = null;
		      ResultSet myRs = null;
		      
		      try {
		         conn = dataSource.getConnection();
		         String sql = "select * from movie";
		         mySt = conn.createStatement();
		         myRs = mySt.executeQuery(sql);
		         
		         while(myRs.next()) {
		            int movie_num = myRs.getInt("movie_num");
		            String title = myRs.getString("title");
		            String genre = myRs.getString("genre");
		            int age_limit = myRs.getInt("age_limit");
		            String running_time = myRs.getString("running_time");
		            String poster = myRs.getString("poster");
		            String trailer_url = myRs.getString("trailer_url");
	                  MovieList_dto tempMovie = new MovieList_dto(movie_num,title,genre,age_limit,running_time,poster, trailer_url);
	                  Movies.add(tempMovie);
		          
		   
		         }
		         return Movies;
		         
		      }
		      finally {
		         myRs.close();
		         mySt.close();
		         conn.close();
		      }
		      
		   }
	
	   public List<reservationTicket_dto> reservationTicketLookUp (int id) throws Exception{

           List<reservationTicket_dto> reservationTickets = new ArrayList<reservationTicket_dto>();
           Connection conn = null;
           PreparedStatement mySt = null;
           ResultSet myRs = null;
                 
           try{
              conn = dataSource.getConnection();
              String sql = "SELECT movie.poster,reservation.index, title, date,time, running_time, theater,group_concat(seat_name) as'seat_name'"
                    +" FROM reservation LEFT JOIN schedule on reservation.sch_num = schedule.sch_num"
                    +" LEFT JOIN movie on movie.movie_num = schedule.movie_num" 
                    +" LEFT JOIN seat on reservation.seat_index = seat.seat_index WHERE cancellations IS NULL AND user_index = ? group by multiple_seat";
              mySt= conn.prepareStatement(sql);
              mySt.setInt(1, id);
              myRs = mySt.executeQuery();

              while(myRs.next()) {
                 

                 reservationTicket_dto reservationTicket = new reservationTicket_dto(
                	   myRs.getString("poster"), 
                       myRs.getInt("index"),
                       myRs.getString("title"), 
                       myRs.getString("date"), 
                       myRs.getString("time"), 
                       myRs.getString("running_time"), 
                       myRs.getInt("theater"), 
                       myRs.getString("seat_name"));

                 reservationTickets.add(reservationTicket);
                 
                 }
                 return reservationTickets;
        
           }finally{
              close(conn, mySt, myRs);
           }   
        }      	
	
	   public List<reservationTicket_dto> canceledTicketLookUp (int id) throws Exception{

           List<reservationTicket_dto> canceledTickets = new ArrayList<reservationTicket_dto>();
           Connection conn = null;
           PreparedStatement mySt = null;
           ResultSet myRs = null;
                 
           try{
              conn = dataSource.getConnection();
              String sql = "SELECT reservation.index, title, date,time, running_time, theater, group_concat(seat_name) as 'seat_name'"
                    +" FROM reservation LEFT JOIN schedule on reservation.sch_num = schedule.sch_num"
                    +" LEFT JOIN movie on movie.movie_num = schedule.movie_num" 
                    +" LEFT JOIN seat on reservation.seat_index = seat.seat_index WHERE cancellations = \"cancel\" AND user_index = ? group by multiple_seat";
              mySt= conn.prepareStatement(sql);
              mySt.setInt(1, id);
              myRs = mySt.executeQuery();

              while(myRs.next()) {
                 

                 reservationTicket_dto canceledTicket = new reservationTicket_dto(
                       myRs.getInt("index"),
                       myRs.getString("title"), 
                       myRs.getString("date"), 
                       myRs.getString("time"), 
                       myRs.getString("running_time"), 
                       myRs.getInt("theater"), 
                       myRs.getString("seat_name"));

                 canceledTickets.add(canceledTicket);
                 
                 }
                 return canceledTickets;
        
           }finally{
              close(conn, mySt, myRs);
           }   
        }  	
		
	public void nonuser_reservation(reservation_dto reservation) throws Exception{
	      Connection conn = null;
	      PreparedStatement pst = null;
	      String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	            
	      try {
	         
	         conn = dataSource.getConnection();

	         String sql = "INSERT INTO reservation"
	         +" (sch_num, seat_index, check_user, nonuser_index,multiple_seat)"
	         +" values(?, ?, ?, ?,?)";
	         
	         pst = conn.prepareStatement(sql);
	      
	         pst.setInt(1,reservation.getSch_num());
	         pst.setInt(2,reservation.getSeat_index());
	         pst.setInt(3,reservation.getCheck_user());
	         pst.setInt(4,reservation.getNonuser_index());
	         pst.setString(5, str);


	         pst.executeUpdate();
	      }catch(Exception e) {
	         e.printStackTrace();      

	      }finally{
	         close(conn, pst, null);         
	      }      
	   }
	   
	   public void user_reservation(reservation_dto reservation) throws Exception{
	      Connection conn = null;
	      PreparedStatement pst = null;
	      String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	      
	       
	      try {
	         conn = dataSource.getConnection();
	                  
	         String sql = "INSERT INTO reservation"
	         +" (sch_num, seat_index, check_user, user_index,multiple_seat)"
	         +" values(?, ?, ?, ?,?)";
	         
	         pst = conn.prepareStatement(sql);

	         pst.setInt(1, reservation.getSch_num());
	         pst.setInt(2, reservation.getSeat_index());
	         pst.setInt(3, reservation.getCheck_user());
	         pst.setInt(4, reservation.getUser_index());
	         pst.setString(5, str);

	         pst.executeUpdate();

	      }catch(Exception e) {
	         e.printStackTrace();      

	      }finally{
	         close(conn, pst, null);         
	      }      
	   }
	   
	
	public List<movie_dto> MovieTime (int moviename1) throws Exception {
	      
	      List<movie_dto> MovieTime = new ArrayList<>();
	      Connection conn = null;
	      Statement mySt = null;
	      ResultSet myRs = null;
	      
	      try {
	         conn = dataSource.getConnection();
	         mySt = conn.createStatement();
	         myRs = mySt.executeQuery("select sch_num,theater,date,time from schedule where movie_num = "+moviename1 );
	         while(myRs.next()) {
	            int sch_num = myRs.getInt("sch_num");
	            int theater = myRs.getInt("theater");
	            String date = myRs.getString("date");
	            String time = myRs.getString("time");
	            movie_dto movietime = new movie_dto(sch_num,theater,date,time);
	            MovieTime.add(movietime);
	         }
	      }catch(Exception e) {
	         e.printStackTrace();
	      }
	      return MovieTime;
	      		      
	   }
	
	   public void joinMember(String id , String name , String birth , String phone_num , String password) {
	         
		      Connection conn;
		      PreparedStatement mySt ;
		      
		      try {
		         conn= dataSource.getConnection();
		         mySt =conn.prepareStatement("insert into user(id,name,birth,phone_num,password) values\r\n"
		               + "(?,?,?,?,?);");   
		         mySt.setString(1, id);
		         mySt.setString(2, name);
		         mySt.setString(3, birth);
		         mySt.setString(4, phone_num);
		         mySt.setString(5, password);
		         
		         mySt.execute();
		      }catch(Exception e) {
		         
		      }
		         
		   
	      
	}
	
	public boolean checkId(String id) throws SQLException, ServletException, IOException {
	      Connection conn;
	      Statement mySt = null;
	      ResultSet myRs = null;
	      
	      conn = dataSource.getConnection();
	      mySt = conn.createStatement();
	      myRs = mySt.executeQuery("select id from user" );
	      
	      List<String> Id = new ArrayList<>();
	      
	      while(myRs.next()) {
	         String id_ = myRs.getString("id");
	         Id.add(id_);
	      }
	      return Id.contains(id);
	   }
	
	public List<reservation_dto> loadSeat(int sch_num) throws Exception{
         List<reservation_dto> seats = new ArrayList<reservation_dto>();
         Connection conn = null;
         //PreparedStatement mySt= null;
         Statement mySt= null;
         ResultSet myRs = null;
         
         try{
            conn = dataSource.getConnection();
            String sql = "SELECT sch_num, seat.seat_index, seat_name FROM seat"
            +" LEFT JOIN reservation on reservation.seat_index =seat.seat_index and sch_num = " +  sch_num
            +" WHERE theater_num = (SELECT schedule.theater FROM schedule WHERE sch_num = " +  sch_num + ")";
            
            //mySt= conn.prepareStatement(sql);
            //pst.setInt(1, sch_num); 
            //myRs = pst.executeQuery();   
            mySt= conn.createStatement();
            myRs = mySt.executeQuery(sql);
            
            while(myRs.next()) {

               reservation_dto seat = new reservation_dto(myRs.getInt("sch_num"),myRs.getInt("seat_index"), myRs.getString("seat_name"));
               seats.add(seat);
            }
            return seats;
      
         }finally {
            close(conn, mySt, myRs);
         }
      }
	
	
	public List<reservationTicket_dto> reservations_complete(List<reservation_dto> reservations) throws Exception {
        //매개변수를 리스트형태로 불러옴
  
        List<reservationTicket_dto> reservations_Ticket = new ArrayList<>();
         Connection conn = null;
         Statement mySt = null;
         ResultSet myRs = null;
 
      
           try{
 
                 conn = dataSource.getConnection();
                 mySt = conn.createStatement();
                 for(int i = 0; i <reservations.size(); i++) {
                  String sql = "SELECT movie.poster, reservation.index,seat_name,theater,date,time,running_time,title "
                       + "FROM reservation JOIN seat ON reservation.seat_index = seat.seat_index "
                       + "JOIN schedule ON reservation.sch_num = schedule.sch_num "
                       + "JOIN movie ON movie.movie_num = schedule.movie_num where seat.seat_index ="+ reservations.get(i).getSeat_index() 
                       + " and reservation.sch_num =" + reservations.get(i).getSch_num();
                 //for문을 통한 sql문을 여러번 돌려서 결과값들을 배열로 반환함

               myRs = mySt.executeQuery(sql);                

                 while(myRs.next()) {
                    reservationTicket_dto reservationTickets = new reservationTicket_dto(
                    		myRs.getString("poster"),
                             myRs.getInt("index"),
                             myRs.getString("title"), 
                             myRs.getString("date"), 
                             myRs.getString("time"), 
                             myRs.getString("running_time"), 
                             myRs.getInt("theater"), 
                             myRs.getString("seat_name"));
                            reservations_Ticket.add(reservationTickets);   
                          
                      }
                 myRs.close();
           }

                
              }finally {
                close(conn,mySt,myRs);
             }
        return reservations_Ticket;
     
        
     }   
	
	
	protected void close(Connection conn, Statement mySt, ResultSet myRs) throws Exception{
		try {		
			if(myRs != null)
				myRs.close();
			if(mySt != null)
				mySt.close();
			if(conn != null)
				conn.close();
			//ResultSet , Statement, connection 닫음
		}catch(Exception e){
			System.out.println("Couldn't close connection: " + e.getMessage());
			e.printStackTrace();
		}
	}

	

	

	

	

}