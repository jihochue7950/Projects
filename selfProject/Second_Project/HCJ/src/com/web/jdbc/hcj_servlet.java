package com.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

/**
 * Servlet implementation class hcj_servlet
 */
@WebServlet("/hcj_servlet")
public class hcj_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	private hcj_model hcj_model;
	
	//어떤 데이터 베이스와 연결할 것인가?
	@Resource(name="jdbc/project")
	private DataSource dataSource;


	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		hcj_model = new hcj_model(dataSource);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		try {	
			String checkPage = request.getParameter("command");
	
			if (checkPage == null) {
				checkPage = "list";
			}
			
			switch(checkPage) {
				case "movieTime":
					movieTime(request, response);
					break;
				case "seatSelection":
					seatSelection(request, response);
					break;
				case "reservation":
					reservation(request, response);
					break;
				case "reservationticket":
					reservationTicket(request, response);
					break;
				case "cancel" :
					cancel(request, response);
					break;
				case "delete" :
					delete(request, response);
					break;
				default:
					listMovies(request, response);
			}
		}catch(Exception e) {
			log("error catch", e);
			throw new ServletException(e);
		}
	}
	
  





      
	   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        
	         String CheckPage = request.getParameter("command");

	         switch(CheckPage){
	            case "checkId":
			         try {
			            checkId(request,response);
			         } catch (ServletException e1) {
			            // TODO Auto-generated catch block
			            e1.printStackTrace();
			         } catch (IOException e1) {
			            // TODO Auto-generated catch block
			            e1.printStackTrace();
			         } catch (SQLException e1) {
			            // TODO Auto-generated catch block
			            e1.printStackTrace();
			         }
			         break;
	            case "JOIN" :
	           
			         try {
			            joinMember(request,response);
			         } catch (ServletException | IOException | SQLException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			         }
			         break;
	            case "nonUser" :
			         try {
			            nonUser(request,response);
			         } catch (ServletException | IOException | SQLException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			         }
			         break;
	            case "Login":
			         try {
			            Login(request,response);
			         } catch (ServletException | IOException | SQLException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			         } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			         break;			               
	         }
	      }
	   //비회원예매를 위한 메소드
	   private void nonUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		   
		      String name = request.getParameter("name");
		      String birth = request.getParameter("birth");
		      String phone_num = request.getParameter("phone_num");

		}
   
	   //로그인 과정을 처리하는 메소드
	      private void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	            
	            String id = request.getParameter("id");
	            String pw = request.getParameter("password");
	           
	            if(hcj_model.Login(id, pw)==true) {
	            	//Model단의 Login 메소드에서 HashMap 결과값이 true일때 id를 session값에 담음
	               request.setAttribute("id",id);
	               request.setAttribute("password",pw);
	               HttpSession session = request.getSession();
	               session.setAttribute("id", id);
	                 
	               Integer sch_num = null;
	              if(session.getAttribute("sch_num") != null){   
	                  sch_num = (int)session.getAttribute("sch_num");   
	              }
	              
	               if(sch_num == null) {    
	            	   //sch_num session 값이 비어있을때 main2 페이지로 이동시켜줌
	                  RequestDispatcher dispatcher = request.getRequestDispatcher("/main2.jsp");
	                   dispatcher.forward(request, response);
	               }else if(sch_num != null) {
	            	   //sch_num session값이 존재 할때 session값을 가지고 reservation을 실행시킴
	                  String checkPage = "loginpage";
	                  session.setAttribute("checkPage", checkPage);
	                  response.sendRedirect(request.getContextPath()+"/hcj_servlet?command=reservation");
	               }
	       
	                 //만약 로그인 메서드가 트루 이면 Logingo.jsp에서 나머지 작업을 수행 (세션을 /Logingo.jsp에서 저장)
	            }else if (hcj_model.Login(id, pw)==false) {
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");     
	                    dispatcher.forward(request, response);
	            }
	            
	         }  
	    

   //회원가입시 ID 중복체크를 위한 메소드
   public void checkId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	      String id = request.getParameter("id");

	      request.setAttribute("id", id);
	      request.setAttribute("check_id", hcj_model.checkId(id));

	     
	      if (hcj_model.checkId(id)==true) {
	    	  //id가 존재하면  true값을 반환하며 CheckId2로 페이지 이동
	         RequestDispatcher dispatcher = request.getRequestDispatcher("/CheckId_2.jsp");     
	         dispatcher.forward(request, response);
	         
	      }else if(hcj_model.checkId(id)==false){
	    	  //id 가 존재하지 않으면 insert받은 id값을 가지고 Join_Member로 이동
	            request.setAttribute("id_", id);   
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/Join_Member.jsp");     
	            dispatcher.forward(request, response);
	      }
	     
	}   
  

   //회원가입을 위한 메소드
   public void joinMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
       //정규표현식으로 input값 제한
       String reg = "^[a-zA-Z]*$";
       String regBirthdate = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
       String regPhoneNumber ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
       String id = request.getParameter("id_check");
       String password = request.getParameter("password");
       String name = request.getParameter("name");
       String birth = request.getParameter("birth");
       String phone_num = request.getParameter("phone_num");
       
       boolean a;
       boolean b;
       boolean c;
       boolean d;
         
       do{

	          b = Pattern.matches(regPhoneNumber, phone_num);
	          c = Pattern.matches(regBirthdate, birth);
	          d = Pattern.matches(reg, name);
         
	          if ( b==false || c ==false ||d == false) {
	        	  // 하나의 정보도 정규식표현에 맞지 않으면 validity_check페이지로 이동
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/validity_check.jsp");     
	                dispatcher.forward(request, response);
	          }
         }while(!b && !c && !d);
       //input값을 제대로 받아온경우 회원정보를 회원테이블에 저장하기 위함
                
         hcj_model.joinMember(id,name,birth,phone_num,password);
        
       
         RequestDispatcher dispatcher = request.getRequestDispatcher("/MovieList.jsp");     
         dispatcher.forward(request, response);
    }

   //예매정보를 예매취소로 업데이트 시켜주기 위함
   private void cancel(HttpServletRequest request, HttpServletResponse response) throws Exception{
	      int index = Integer.parseInt(request.getParameter("tempindex"));
	      String multiple_seat = hcj_model.indexToMultipleseat(index);
	      hcj_model.cancel(index, multiple_seat);
	      
	      RequestDispatcher dispatcher = request.getRequestDispatcher("hcj_servlet?command=reservationticket");     
	      dispatcher.forward(request, response);
   }
   //예매정보를 table에서 삭제하기 위함
	      private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
	          int index = Integer.parseInt(request.getParameter("tempindex"));
	          String multiple_seat = hcj_model.indexToMultipleseat(index);
	          hcj_model.delete(index, multiple_seat);
	          
	          RequestDispatcher dispatcher = request.getRequestDispatcher("hcj_servlet?command=reservationticket");     
	          dispatcher.forward(request, response);
	       }
   
   //예약정보를 확인하기 위한 메소드
   private void reservationTicket(HttpServletRequest request, HttpServletResponse response) throws Exception{
	   //새션에서 (Stirng)id 값 받기
	   HttpSession session = request.getSession();
	   String id = (String)session.getAttribute("id"); 

	   //id 값  (int)index로 변경 
	   int user_id = hcj_model.sessionidChangeUserTable(id);


	  List<reservationTicket_dto> reservationTicket = hcj_model.reservationTicketLookUp(user_id);
	  request.setAttribute("reservationTicketTemp", reservationTicket);
	  
	  List<reservationTicket_dto> canceledTicket = hcj_model.canceledTicketLookUp(user_id);	  
	  request.setAttribute("canceledTicketTemp", canceledTicket);
	  //reservation 테이블에 예매취소를 업데이트 시켜주기 위함

	  RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationCheck.jsp");	  
	  dispatcher.forward(request, response);	  

   }	
   
   @SuppressWarnings("unused")
private void reservation(HttpServletRequest request, HttpServletResponse response) throws Exception{
	      
	      //새션에서 (Stirng)id 값 받기
	         HttpSession session = request.getSession();
	         String id = (String)session.getAttribute("id");

	      if(id == null) {
	    	  //session id가 비어있으면 
	         int sch_num = Integer.parseInt(request.getParameter("sch_num"));
	         String[] seat_index = request.getParameterValues("seat_to_reserve");
	         session.setAttribute("sch_num", sch_num);
	         session.setAttribute("seat_index", seat_index);     
	         //seat_index가 비어있으면(좌석선택이 되지않으면) Checkseat으로 페이지 이동
	         if(seat_index==null){RequestDispatcher dispatcher = request.getRequestDispatcher("/Checkseat.jsp");
	            dispatcher.forward(request, response);}
	          //response.sendRedirect(request.getContextPath()+"/User_nonUser.jsp");
	         //seat_index에 값이 있으면 User_nonUser페이지로 이동
	         else if(seat_index !=null) {RequestDispatcher dispatcher = request.getRequestDispatcher("/User_nonUser.jsp");
	            dispatcher.forward(request, response);//만약 받아온 세션아이디가 null이면 User_nonUser page로가서 로그인이나 비회원 로그인을 하도록 유도
	         }
	      }else if(!id.equals("null")) {
	    	  //session id가 있을때
	         String checkPage = (String)session.getAttribute("checkPage");
	         
	            //id 값  (int)index로 변경 
	               int user_id = hcj_model.sessionidChangeUserTable(id);
	               // 세션 아이디가 null이 아닐 경우에 reservation 메서드 내용이 실행됨
	              
	              int sch_num = 0;
	              String[] seat_index = null;
	                 if(checkPage == null) {
	                    sch_num = Integer.parseInt(request.getParameter("sch_num"));
	                        seat_index = request.getParameterValues("seat_to_reserve");
	                 } else if(checkPage.equals("loginpage")) {
	                    sch_num = (int)session.getAttribute("sch_num");    
	                   seat_index = (String[])session.getAttribute("seat_index");  
	                 }
	                 //seat_index가 비어있으면 Checkseat 페이지로 이동
	                 if(seat_index==null){RequestDispatcher dispatcher = request.getRequestDispatcher("/Checkseat.jsp");
			            dispatcher.forward(request, response);}
	                else if(seat_index != null) {
	               int check_user = 1;	//ckeck_user=1 > 회원인경우
	               
	               int nonuser_index = 0;	//check_user=0 > 비회원인경우
	               int user_index = user_id; 
	               
	               List<reservation_dto> reservations = new ArrayList<>();
	               //여러개의 좌석이 선택된 경우 List로 받아오기 위함
	                 for (int i = 0; i <seat_index.length; i++) {
	                     reservation_dto reservation = new reservation_dto(sch_num, Integer.parseInt(seat_index[i]), check_user, nonuser_index, user_index);
	                     if(check_user == 0) {
	                         hcj_model.nonuser_reservation(reservation);
	                      }else if(check_user == 1) {         
	                         hcj_model.user_reservation(reservation);
	                      }  
	                     reservations.add(reservation);                     
	                 }
	              
	                 session.removeAttribute("checkPage");
	                 session.removeAttribute("sch_num");
	                 session.removeAttribute("seat_index");
	              
	            // 예약이 완료되면 예약완료 페이지로 넘겨줌
	              List<reservationTicket_dto> reservationConfirm = hcj_model.reservations_complete(reservations);
	              request.setAttribute("reservationConfirm", reservationConfirm);
	              RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationConfirm.jsp");
	              dispatcher.forward(request, response);   }         
	      } 
	   }   
   //영화들을 리스트화 해서 배열로 불러오기 위한 메소드
	public void listMovies(HttpServletRequest request, HttpServletResponse response) throws Exception{

	    try {
	        List<MovieList_dto> Movies = hcj_model.getMovieList();
	        request.setAttribute("movie_list", Movies);
	     }catch(Exception e) {
	        e.printStackTrace();
	     }
	   
	      RequestDispatcher dispatcher = request.getRequestDispatcher("/MovieList.jsp");     
	      dispatcher.forward(request, response);
	   }
   
   
	

	//영화 시간을 불러오기 위한 메소드
	public void movieTime(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        // String movietitle = request.getParameter("moviename");
         //int movie_num = hcj_model.titleChangeMoiveNum(movietitle);         
         int movie_num = Integer.parseInt(request.getParameter("movieNum"));
         
         try {
              List<MovieList_dto> Movies = hcj_model.getMovieList();
              request.setAttribute("movie_info", Movies.get(movie_num-1));
           }catch(Exception e) {
              e.printStackTrace();
           }
         
         List<movie_dto> movieschedule = hcj_model.MovieTime(movie_num);
         request.setAttribute("scheduleList", movieschedule);
         
          RequestDispatcher dispatcher = request.getRequestDispatcher("/MovieTime.jsp");
            dispatcher.forward(request, response);
                 
      }      		   
	
	//좌석선택을 위한 메소드
	private void seatSelection(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//schedule테이블의 sch_num을 변수로 받아 좌석을 조회함
		int sch_num = Integer.parseInt(request.getParameter("sch_num"));
		request.setAttribute("sch_num", sch_num);
		//schedule의 좌석마다 교유번호를 부여해 해당하는 스케줄의 좌석을 불러옴
		List<reservation_dto> seats = hcj_model.loadSeat(sch_num);
		request.setAttribute("select_seat", seats);	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/SeatSelection.jsp");	  
		dispatcher.forward(request, response);
	}
}