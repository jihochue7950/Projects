package com.moiza.controller;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.moiza.entity.UsergroupEntity;
import com.moiza.dto.UsergroupUserDto;
import com.moiza.entity.Authorities;
import com.moiza.entity.ImgEntity;

import com.moiza.entity.MgroupEntity;
import com.moiza.entity.PostEntity;
import com.moiza.entity.UserEntity;
import com.moiza.service.MoizaService;

@Controller // MVC의 컨트롤러라는 것을 알려줌
public class MoizaController {

   @Autowired
   private MoizaService moizaService;
   
   @GetMapping("/")
      public String showHome(Authentication authentication, Model theModel) {
         
         try {
            // 스프링 시큐리티 로그인 아이디를 가져와서,
            String userId = authentication.getName();
            // 사용자가 user그룹에서 로그인한 index를 기준으로
            int userIndex = moizaService.UseridChangeUserindex(userId);

            // 유저인덱스를 기준으로 그룹장으로 되어있는 모든 정보를 가지고옴(admin, normal)
            List<MgroupEntity> theMyMgroup = moizaService.getSubscribedMgroup(userIndex);
            theModel.addAttribute("theMyMgroup", theMyMgroup);

         } catch (NullPointerException e) {
            System.out.println(e);
         }

         List<MgroupEntity> bestGroup = moizaService.bestGroup();
         theModel.addAttribute("bestGroup", bestGroup);

         List<MgroupEntity> randomGroup = moizaService.randomGroup();
         theModel.addAttribute("randomGroup", randomGroup);
      
         return "main";
      }

   @GetMapping("/showMyLoginPage")
   public String showMyLoginPage() {
      return "custom_login";
   }

   @GetMapping("/join")
   public String joinUser(Model themodel) {
      //유저 엔티티 생성자를 만들어서 join_user.jsp로 보낸다 
      UserEntity user = new UserEntity();
      themodel.addAttribute("user", user);
      return "join_user";
   }

   @GetMapping("/saveUser")
   public String saveUser(@ModelAttribute("user") UserEntity user,
         @ModelAttribute("authorities") Authorities authorities,Model themodel) {
         String reguser_name = "^[ㄱ-ㅎ|가-힣]+$";
          String regusername = "^[a-z]+[a-z0-9]{5,19}$";
          String regBirthdate = "^([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))$";
          String regPhoneNumber ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
          String username = user.getUsername();          
          String birth = user.getUser_birth();
          String phone_num = user.getUser_phone();
          String user_name = user.getUser_name();
          boolean a;
          boolean b;
          boolean c;
          boolean d;
          //정규표현식을 do while 반복문에 넣어서 pattern.matches라는 함수를 이용해 원하는 형식의 데이터를 검사한다
          //원하는 형식으로 받아와서 형식에 알맞으면 반복문을 빠져나간다
          do{

                a = Pattern.matches(regPhoneNumber, phone_num);
                b = Pattern.matches(regBirthdate, birth);
                c = Pattern.matches(regusername, username);
                d = Pattern.matches(reguser_name, user_name);
                if ( a==false || b ==false ||c == false||d == false) {
                   
                   themodel.addAttribute("error", "정해진 형식에 맞게 작성해주세요");
                   return "join_user";
                   // false값일 경우 join_user.jsp에 error메세지를 보내준다
                }
            }while(!a && !b && !c && !d);
      
                   
      user.setPassword("{noop}" + user.getPassword());
      //비밀번호 설정시 {noop}를 넣어서 저장해준다
      //어떤 이유로 든 구성된 암호를 인코딩하지 않으려면 NoOpPasswordEncoder를 사용하여야 하는데 이를 위해 패스워드에 {noop}식별자를 붙여준다
      boolean IdCheck = moizaService.IdCheck(user.getUsername());
      // 아이디 중복검사 하는 매서드 boolean 값을 반환한다
      if(IdCheck == false) {
         //false 일 경우 원하는메서드 실행
      moizaService.saveUser(user);
      moizaService.saveAuthority(authorities);
      //메서드의 실행 순서가 중요함 외래키가 걸려 있어서 유저 테이블에 먼저 저장을 해주고 시큐리티를 위한 테이블에 후에 저장함
      }else if(IdCheck == true) {
         themodel.addAttribute("errorId","중복된 아이디가 있습니다");
         return "join_user";
         //중복된 아이디가 있을 경우 join_user.jsp에 error메세지를 보냄
      }
      return "redirect:/";
   }
   
   @GetMapping(value = "/logout")
   public String logout(HttpServletRequest request, HttpServletResponse response) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null && auth.isAuthenticated()) {
         new SecurityContextLogoutHandler().logout(request, response, auth);
      }
      return "redirect:/";
      //로그아웃을 위한 별도의 컨트롤러 
   }

      @GetMapping("/group_main_post")
      @PreAuthorize("isAuthenticated()")//@PreAuthorize 요청이 들어와 함수를 실행하기 전에 권한을 검사하는 어노테이션 ,isAuthenticated()현재 사용자가 익명이 아니라면 (로그인 상태라면) true
      public String group_main_post(Authentication authentication, @RequestParam("mgroupIndex") int groupIndex, Model theModel) {
         MgroupEntity theGroup = moizaService.getConnectedGroupInfo(groupIndex);
         theModel.addAttribute("mgroup", theGroup);
            
         List<PostEntity> thePosts = moizaService.getConnectedGroupPosts(groupIndex);
         //그룹 인덱스를 가지고 포스트테이블을 리스트로 받아온다
         theModel.addAttribute("post", thePosts);
         try {
            //모임 회원등급을 기준으로
            String userId = authentication.getName();
            int userIndex = moizaService.UseridChangeUserindex(userId);
            //userId로 해당 아이디의 인덱스를 가져오는 메서드
            List<UsergroupEntity> theUsergroups = moizaService.getUserRole(userIndex, groupIndex);
            //유저 인덱스와 그룹 인덱스를 가지고 해당 유거의 유저그룹에서 role을 가져옴            
            //모임에 가입되어 있지 않다면 guest로
            if(theUsergroups.isEmpty()) {
               theModel.addAttribute("theUsergroupRole", "guest");   
            }else{
               //admin/normal/employee으로 분류함
               String theUsergroupRole = theUsergroups.get(0).getUsergroup_user_role();
               theModel.addAttribute("theUsergroupRole", theUsergroupRole);
            }
            
            int count = moizaService.countMember(groupIndex);
            // 그룹의 가입된 맴버 수를 불러오는 메서드
            theModel.addAttribute("count",count);
         } catch (NullPointerException e) {
            //비회원일떄는 로그인,회원가입 유도
            System.out.println(e);
         
            return "redirect:/showMyLoginPage";
         }
         
         return "group_main_post";
      }

   @GetMapping("/writing_post")
   @PreAuthorize("isAuthenticated()")
   public String writing_post(@RequestParam("mgroupIndex") int mgroupIndex, Authentication authentication, Model theModel) {
      // 스프링 시큐리티 로그인 아이디를 가져오는 다른 방법
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String userId = userDetails.getUsername();
      // 사용자가 user그룹에서 로그인한 index를 기준으로
      int userIndex = moizaService.UseridChangeUserindex(userId);
      // 유저그룹 테이블의 유저그룹인덱스를 가져와야
      List<UsergroupEntity> theUserGroup = moizaService.getUserGroup(userIndex, mgroupIndex);
      // 글을 담을 빈객체를 생성,유저그룹인덱스를 담아서 글쓰기로 넘어감
      PostEntity bowOfPost = new PostEntity();
      int getUsergroup_index = theUserGroup.get(0).getUsergroup_index();
      bowOfPost.setPost_usergroup_index(getUsergroup_index);
      theModel.addAttribute("bowOfPost", bowOfPost);
      return "writing_post";
   }

   @GetMapping("/save_written_post")
   @PreAuthorize("isAuthenticated()")
   public String save_Written_Post(@ModelAttribute("bowOfPost") PostEntity bowOfPost, Model theModel) {
      moizaService.saveWrittenPost(bowOfPost);
      // bowOfPost에 있는 getUsergroup_index를 기준으로 mgroupIndex를 가져옴
      int getUsergroup_index = bowOfPost.getPost_usergroup_index();
      int mgroupIndex = moizaService.findMgroupIndexBase(getUsergroup_index);
      theModel.addAttribute("mgroupIndex", mgroupIndex);
      return "redirect:/group_main_post";
   }
   
   @GetMapping("/ViewGroupMemberSetting")
   @PreAuthorize("isAuthenticated()")
   public String ViewGroupMembers(@RequestParam("mgroupIndex") int mgroupIndex, Authentication authentication, Model theModel) {
      String userId = authentication.getName();
      int userIndex = moizaService.UseridChangeUserindex(userId);
      List<UsergroupEntity> theUsergroups = moizaService.getUserRole(userIndex, mgroupIndex);
      //admin/normal으로 분류함
      String theUsergroupRole = theUsergroups.get(0).getUsergroup_user_role();
      theModel.addAttribute("theUsergroupRole", theUsergroupRole);

      if(theUsergroupRole.equals("admin")) {
         List<UsergroupUserDto> GroupUserInfo = moizaService.GroupUserInfo(mgroupIndex, 0);
         theModel.addAttribute("GroupUserInfo", GroupUserInfo);
         //가져온 역할이 admin이면 모임 전체의 유저를 담아주고
      }else if(theUsergroupRole.equals("normal")) {
         List<UsergroupUserDto> GroupMyInfo = moizaService.GroupUserInfo(mgroupIndex, userIndex);
         theModel.addAttribute("GroupMyInfo", GroupMyInfo.get(0));
         //가져온 역할이 normal이면 자신의 정보만 리스트에 담아준다
      }
      return "viewGroupMemberSetting";
   }

      @GetMapping("/beforeGroupCreation")
      public String beforeGroupCreation(Model theModel, @RequestParam(value = "img_index", defaultValue="0")int img_index) {
         MgroupEntity mgroup = new MgroupEntity();
         mgroup.setMgroup_img(img_index);
         theModel.addAttribute("mgroup", mgroup);
//         List<ImgEntity> theImg = moizaService.getImg();
//         if(img_index > 0) {
//            theModel.addAttribute("theImg", theImg.get(img_index-1));
//         }
         ImgEntity theImg = moizaService.getImg(img_index);
         theModel.addAttribute("theImg", theImg);
         return "groupRegistry";
      }

   @GetMapping("/groupCreation")
   public String groupCreation(Authentication authentication, @ModelAttribute("mgroup") MgroupEntity mgroup) {
      //@ModelAttribute 사용에서 뷰로부터 form tag의 path로 받아옴 
      if (mgroup.getMgroup_title() == null || mgroup.getMgroup_title().length() == 0   
            || mgroup.getMgroup_img() == 0
            || mgroup.getMgroup_maincategory().equals("0")
            || mgroup.getMgroup_middlecategory().equals("0")){
      //받아와야 하는 파라미터들이 null이거나 0일경우 해당 파라미터를 다시 받아오게끔 유도
         return "beforeGroupCreation";
      }
      
      moizaService.saveGroup(mgroup);
      
      String userId = authentication.getName();
      int userIndex = moizaService.UseridChangeUserindex(userId);
      
      UsergroupEntity usergroupEntity = new UsergroupEntity();
      usergroupEntity.setUsergroup_user_index(userIndex);
      usergroupEntity.setUsergroup_group_index(mgroup.getMgroup_index());
      usergroupEntity.setUsergroup_user_role("admin");
      moizaService.makeTheLeader(usergroupEntity);
      //그룹을 만들고 나면 해당 유저를 그룹의 리더로 임명하는 메서드
      return "redirect:/";
   }
   

   @GetMapping("/select_img")
   public String select_img(Model theModel, @ModelAttribute("mgroup") MgroupEntity mgroup) {
      List<ImgEntity> theImg = moizaService.getImg();
      theModel.addAttribute("theImg", theImg);
      theModel.addAttribute("mgroup", mgroup);
      //이미지를 select_img.jsp에 뿌려준다
      return "select_img";
      // 이미지 
   }
   
   @GetMapping("/joingroup")
   @PreAuthorize("isAuthenticated()")
   public String joingroup(@RequestParam("mgroupIndex") int mgroupIndex, Authentication authentication,
         Model theModel) {
      // 스프링 시큐리티 로그인 아이디를 가져오는 다른 방법
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String userId = userDetails.getUsername();
      // 사용자가 user그룹에서 로그인한 index를 기준으로
      int userIndex = moizaService.UseridChangeUserindex(userId);
      // 유저그룹 테이블의 유저그룹인덱스를 가져와야
      //List<UsergroupEntity> theUserGroup = moizaService.getUserGroup(userIndex, mgroupIndex);
      moizaService.savejoingroup(userIndex, mgroupIndex);
      //UsergroupEntity usergroup = new UsergroupEntity();
      //int getUsergroup_index = theUserGroup.get(0).getUsergroup_index();
      //usergroup.setUsergroup_index(getUsergroup_index);
      //theModel.addAttribute("usergroup", usergroup);
      theModel.addAttribute("mgroupIndex", mgroupIndex);
      return "redirect:/group_main_post";
   }
   
      @GetMapping("/Mypage")
      public String mypage(Authentication authentication , Model themodel) {
         String userId = authentication.getName();
         themodel.addAttribute("userId",userId);
         return "mypage";
      }
      
      @GetMapping("/modification")
      public String modification(@RequestParam("userId") String userId,Model themodel) {
         
         List<UserEntity> theUsers = moizaService.theUserInformation(userId);
         themodel.addAttribute("users", theUsers); 
         //유저 아이디를 불러와서 해당 유저 정보를 modification.jsp로 보내준다 수정 할 수 있게끔
         return "modification";
      }
      
      @GetMapping("/userModification")
      public String userModification(@RequestParam("user_index") int user_index,@RequestParam("user_phone") String user_phone ,
            @RequestParam("password") String password) {
         moizaService.updateUserInfo(user_index,user_phone,password);
         //수정한 데이터를 다시 업데이트해준다
         return "redirect:/";
      }
      
      @GetMapping("/withdraw")
         public String withdraw(@RequestParam("user_index") int user_index, Model themodel) {
            themodel.addAttribute("user_index", user_index);
            moizaService.DeleteUser(user_index);
            return "withdraw";
            //해당 유저를 삭제해주기 위한 메서드 유저 인덱스를 받아와서 실행
         }
      
      @GetMapping("/search")
      public String search(@RequestParam("searchGroup") String searchGroup, Model themodel) {
         //입력받은 문자열을 스트링으로 받아와서 searchGroup()로 넘긴다
         List<MgroupEntity> searchGroups = moizaService.searchGroup(searchGroup);
         // 입력받은 문자에 해당하는 그룹들을 배열로 받아와서 뷰로 보내준다
         themodel.addAttribute("searchGroups", searchGroups);
         return "searchPage";
         
      }
      
   @GetMapping("/controlmygroup")
   public String controlmygroup(Authentication authentication, Model theModel) {
      try {
         // 스프링 시큐리티 로그인 아이디를 가져와서,
         String userId = authentication.getName();
         // 사용자가 user그룹에서 로그인한 index를 기준으로
         int userIndex = moizaService.UseridChangeUserindex(userId);
         // 유저인덱스를 기준으로 그룹장으로 되어있는 모든 정보를 가지고옴(admin)
         List<MgroupEntity> theleaderMgroup = moizaService.getmygroup(userIndex, "admin");
         theModel.addAttribute("theleaderMgroup", theleaderMgroup);
         
         // 유저인덱스를 기준으로 가입중인 모임의 모든 정보를 가지고옴(normal)
         List<MgroupEntity> thejoinMgroup = moizaService.getmygroup(userIndex, "normal");
         theModel.addAttribute("thejoinMgroup", thejoinMgroup);

         // 유저인덱스를 기준으로 가입대기중인 모임의 모든 정보를 가지고옴(employee)
         List<MgroupEntity> theWaitingMgroup = moizaService.getmygroup(userIndex, "employee");
         theModel.addAttribute("theWaitingMgroup", theWaitingMgroup);

      } catch (NullPointerException e) {
         System.out.println(e);
      }
      return "controlmygroup";
   }
   
   @GetMapping("/ApprovNonMembers")
   @PreAuthorize("isAuthenticated()")
   public String ApprovNonMembers(@RequestParam("usergroup_index")int usergroup_index, Model theModel) {
      UsergroupEntity UsergroupInfo = moizaService.getUsergroupInfo(usergroup_index);
      //유저 그룹에서 유저의 정보를 불러온다
      UsergroupInfo.setUsergroup_user_role("normal");
      // 불러온 정보중에 role를 normal로 세팅하고
      moizaService.nonMemberRegistration(UsergroupInfo);
      // 다시 저장 한다
      theModel.addAttribute("mgroupIndex", UsergroupInfo.getUsergroup_group_index());
      return "redirect:/ViewGroupMemberSetting";
   }
   
   @GetMapping("/RejectNonMembers")
   @PreAuthorize("isAuthenticated()")
   public String RejectNonMembers(@RequestParam("usergroup_index")int usergroup_index, Model theModel) {
      //모임 가입 거절하기
      UsergroupEntity UsergroupInfo = moizaService.getUsergroupInfo(usergroup_index);
      //usergroup_user_role을 수정함.
      //UsergroupInfo.setUsergroup_user_role("denial");
      //moizaService.nonMemberRegistration(UsergroupInfo);
      //usergroup_user_role을 삭제함.
      moizaService.exportGroup(UsergroupInfo);
      theModel.addAttribute("mgroupIndex", UsergroupInfo.getUsergroup_group_index());
      return "redirect:/ViewGroupMemberSetting";
   }
   
   @GetMapping("/kickout")
   @PreAuthorize("isAuthenticated()")
   public String kickout(@RequestParam("usergroup_index")int usergroup_index, Model theModel) {
      //추방
      UsergroupEntity UsergroupInfo = moizaService.getUsergroupInfo(usergroup_index);
      moizaService.exportGroup(UsergroupInfo);
      //내그룹에 가입된 유저의 정보를 정보 가져와야함 
      theModel.addAttribute("mgroupIndex", UsergroupInfo.getUsergroup_group_index());
      return "redirect:/ViewGroupMemberSetting";
   }
   
   @GetMapping("/goout")
   @PreAuthorize("isAuthenticated()")
   public String goout(@RequestParam("usergroup_index")int usergroup_index, Model theModel) {
      //탈퇴
      UsergroupEntity UsergroupInfo = moizaService.getUsergroupInfo(usergroup_index);
      moizaService.exportGroup(UsergroupInfo);
      return "redirect:/";
   }
   @GetMapping("/HandOverSeats")
   @PreAuthorize("isAuthenticated()")
   public String HandOverSeats(@RequestParam("usergroup_index")int usergroup_index, @RequestParam("mgroupIndex")int mgroupIndex, Authentication authentication, Model theModel) {
      //모임장 위임 하기
      UsergroupEntity UsergroupInfo = moizaService.getUsergroupInfo(usergroup_index);
      UsergroupInfo.setUsergroup_user_role("admin");
      moizaService.nonMemberRegistration(UsergroupInfo);
   
      //나는 일반 회원으로 돌아감
      String userId = authentication.getName();
      // 사용자가 user그룹에서 로그인한 index를 기준으로
      int userIndex = moizaService.UseridChangeUserindex(userId);
      
      List<UsergroupEntity> UsergroupMyInfos = moizaService.getUserGroup(userIndex, mgroupIndex);
      UsergroupEntity UsergroupMyInfo = UsergroupMyInfos.get(0);
      UsergroupMyInfo.setUsergroup_user_role("normal");
      moizaService.nonMemberRegistration(UsergroupMyInfo);
      
      //모임관리하기페이지에 들어 갈수 있도록정보를 다시 불러옴
      theModel.addAttribute("mgroupIndex", UsergroupMyInfo.getUsergroup_group_index());
      
      
      return "redirect:/ViewGroupMemberSetting";
   }
      @GetMapping("/DeleteGroup")
      public String DeleteGroup(Model theModel ,HttpServletRequest request,@RequestParam("mgroupIndex") int mgroupIndex,Authentication authentication,@RequestParam("count") int count) {
         HttpSession session = request.getSession();
         /* session.removeAttribute("errorDelete"); */
         System.out.println("test"+count);
         if(count ==1) {
            //받아온 카운터의 값으로 회원의 수를 조건으로 걸어준뒤 1명 즉 방장만있으면 그룹이 삭제가 가능하게 유도
         moizaService.DeleteGroupsAtUserGroup(mgroupIndex);      
         moizaService.DeleteGroup(mgroupIndex);
         }else if(count != 1){
            //만약 count가 1이 아니면 남아있는 회원이 있다는 뜻으로 삭제를 못하게 유도
            System.out.println("test2"+count);
            session.setAttribute("errorDelete", "회원이 존재합니다, 그룹장을 위임하세요");
            theModel.addAttribute("mgroupIndex", mgroupIndex);
            return "redirect:/group_main_post";
            }
         return "redirect:/";
      }
      @GetMapping("/like")
      public String like(@RequestParam("post_index") int like, Model themodel,Authentication authentication, @RequestParam("mgroupIndex") int groupIndex) {
         
         moizaService.pluslike(like);
         //좋아요 버튼을 눌러주면 쿼리문으로 플러스 1을 해줌
         MgroupEntity theGroup = moizaService.getConnectedGroupInfo(groupIndex);
         themodel.addAttribute("mgroup", theGroup);
            
         List<PostEntity> thePosts = moizaService.getConnectedGroupPosts(groupIndex);
         themodel.addAttribute("post", thePosts);
         try {
            //모임 회원등급을 기준으로
            String userId = authentication.getName();
            int userIndex = moizaService.UseridChangeUserindex(userId);
            List<UsergroupEntity> theUsergroups = moizaService.getUserRole(userIndex, groupIndex);
         
            //모임에 가입되어 있지 않다면 guest로
            if(theUsergroups.isEmpty()) {
               themodel.addAttribute("theUsergroupRole", "guest");   
            }else{
               //admin/normal/employee으로 분류함
               String theUsergroupRole = theUsergroups.get(0).getUsergroup_user_role();
               themodel.addAttribute("theUsergroupRole", theUsergroupRole);
            }
            
            int count = moizaService.countMember(groupIndex);
            themodel.addAttribute("count",count);
         } catch (NullPointerException e) {
            //비회원일떄는 로그인,회원가입 유도
            System.out.println(e);
         
            return "redirect:/showMyLoginPage";
         }
         //좋아요를 실행한 뒤 모든 정보를 가지고 다시 돌아가게 유도함
         return "group_main_post";
         
         
      }
      @GetMapping("/searchonmap")
      public String searchmap(@RequestParam("address") String address,Model themodel) {
         //지도에서 지역을 검색하면 그 값을 adress로 받아와서 검색함
         List<MgroupEntity> searchGroups = moizaService.searchaddress(address);
            themodel.addAttribute("searchGroups", searchGroups);
         return "searchPage";
      }
      @GetMapping("/searchmap")
      public String searchmap() {
         return "searchmap";
      }
      
      
      @GetMapping("/deletePost")
      public String deletePost(@RequestParam("mgroupIndex") int groupIndex, @RequestParam("post_index") int post_index, Model theModel, Authentication authentication) {
         PostEntity deletedPost = new PostEntity();
         deletedPost.setPost_index(post_index);
         //포스트 인덱스를 받아와서 엔티티에 셋 해주고 
         moizaService.deletePost(deletedPost);
         //딜리트 메서드 실행
         theModel.addAttribute("mgroupIndex", groupIndex);
         return "redirect:/group_main_post";
      }
      
   @GetMapping("/edit_post")
   @PreAuthorize("isAuthenticated()") 
   public String writing_post(@RequestParam("mgroupIndex") int mgroupIndex, @RequestParam("post_index") int post_index, @RequestParam("post_usergroup_index") int post_usergroup_index, @RequestParam("post_maintext") String post_maintext, HttpServletRequest request,Authentication authentication, Model theModel) {
      PostEntity editedPost = new PostEntity();
      editedPost.setPost_index(post_index);
      editedPost.setPost_usergroup_index(post_usergroup_index);
      editedPost.setPost_maintext(post_maintext);
      //받아온 파라미터를 엔티티에 셋해주고 해당 정보를 edit_post.jsp에 뿌려준다
      HttpSession session = request.getSession();
        session.setAttribute("mgroupIndex", mgroupIndex);
      theModel.addAttribute("editedPost", editedPost);
      return "edit_post";
   }
   
   @GetMapping("/save_edit_post")
   @PreAuthorize("isAuthenticated()")
   public String writing_post(@ModelAttribute("editedPost") PostEntity editedPost, HttpServletRequest request, Authentication authentication, Model theModel) {
      moizaService.saveEditedPost(editedPost);
      //수정된 editedPost를 저장해준다
      HttpSession session = request.getSession();
      String mgroupIndex = String.valueOf(session.getAttribute("mgroupIndex"));
      theModel.addAttribute("mgroupIndex", mgroupIndex);
      session.removeAttribute("mgroupIndex");
      return "redirect:/group_main_post";
   }
     
}