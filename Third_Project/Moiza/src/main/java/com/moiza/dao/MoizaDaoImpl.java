package com.moiza.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.moiza.controller.MoizaController;
import com.moiza.dto.UsergroupUserDto;
import com.moiza.entity.Authorities;
import com.moiza.entity.ImgEntity;

import com.moiza.entity.MgroupEntity;
import com.moiza.entity.PostEntity;
import com.moiza.entity.UserEntity;
import com.moiza.entity.UsergroupEntity;

@Repository
public class MoizaDaoImpl implements MoizaDao {

   @Autowired
   private SessionFactory sessionFactory;
   
   private Model themodel;

   private DataSource dataSource;

   public MoizaDaoImpl(DataSource theDataSource) {
      dataSource = theDataSource;
   }

   @Override
   public int UseridChangeUserindex(String userId) {
      Session currentSession = sessionFactory.getCurrentSession();
      String hql = "from UserEntity where username = :userId";
      Query<UserEntity> theQuery = currentSession.createQuery(hql, UserEntity.class);
      theQuery.setParameter("userId", userId);
      List<UserEntity> theUserEntity = theQuery.getResultList();
      return theUserEntity.get(0).getUser_index();

   }

   @Override
   public List<MgroupEntity> getSubscribedMgroup(int userIndex) {

      List<MgroupEntity> theSubscribedMgroup = new ArrayList<MgroupEntity>();
      Connection conn = null;
      Statement mySt = null;
      ResultSet myRs = null;

      try {
         conn = dataSource.getConnection();
         String sql ="SELECT * FROM mgroup join usergroup"
                  +" ON mgroup.mgroup_index = usergroup.usergroup_group_index"
                  +" WHERE usergroup_user_index = "+ userIndex
                  +" and usergroup_user_role = (\"admin\" or \"normal\") limit 5";
         
         mySt = conn.createStatement();
         myRs = mySt.executeQuery(sql);
         while (myRs.next()) {

            MgroupEntity group = new MgroupEntity();
            group.setMgroup_index(myRs.getInt("mgroup_index"));
            group.setMgroup_title(myRs.getString("mgroup_title"));
            group.setMgroup_img(myRs.getInt("mgroup_img"));
            group.setMgroup_img_url(myRs.getString("mgroup_img_url"));
            group.setMgroup_introduce(myRs.getString("mgroup_introduce"));
            group.setMgroup_maincategory(myRs.getString("mgroup_maincategory"));
            group.setMgroup_middlecategory(myRs.getString("mgroup_middlecategory"));

            group.setMgroup_local_name(myRs.getString("mgroup_local_name"));
            group.setMgroup_minage(myRs.getInt("mgroup_minage"));
            group.setMgroup_maxage(myRs.getInt("mgroup_maxage"));
            group.setMgroup_gender(myRs.getString("mgroup_gender"));
            group.setMgroup_limit(myRs.getInt("mgroup_limit"));


            theSubscribedMgroup.add(group);
         }
         myRs.close();
         mySt.close();
         conn.close();

      } catch (SQLException e) {
         e.printStackTrace();
      }

      return theSubscribedMgroup;
   }
   
    @Override
    public List<MgroupEntity> bestGroup() {
       
       List<MgroupEntity> bestGroup = new ArrayList<MgroupEntity>();
       Connection conn = null;
       Statement mySt = null;
       ResultSet myRs = null;

       try {
          conn = dataSource.getConnection();

          String sql = "SELECT mgroup_title,"
                + "any_value(mgroup_index) as mgroup_index,"                  
                + "any_value(mgroup_img) as mgroup_img,"
                   + "any_value(mgroup_img_url) as mgroup_img_url,"
                   + "any_value(mgroup_introduce) as mgroup_introduce,"
                + "any_value(mgroup_maincategory) as mgroup_maincategory,"
                + "any_value(mgroup_middlecategory) as mgroup_middlecategory,"

                   + "any_value(mgroup_local_name) as mgroup_local_name,"
                   + "any_value(mgroup_minage) as mgroup_minage,"
                   + "any_value(mgroup_maxage) as mgroup_maxage,"
                   + "any_value(mgroup_gender) as mgroup_gender,"
                   + "any_value(mgroup_limit) as mgroup_limit,"

                + "any_value(post_like) as post_like FROM usergroup "
                + "join mgroup ON mgroup.mgroup_index = usergroup.usergroup_group_index "
                + "join post on usergroup.usergroup_index = post_usergroup_index group by mgroup_title order by post_like desc limit 3";
          
          mySt = conn.createStatement();
          myRs = mySt.executeQuery(sql);
          while (myRs.next()) {

         MgroupEntity group = new MgroupEntity();
      group.setMgroup_index(myRs.getInt("mgroup_index"));
      group.setMgroup_title(myRs.getString("mgroup_title"));
      group.setMgroup_img(myRs.getInt("mgroup_img"));
         group.setMgroup_img_url(myRs.getString("mgroup_img_url"));
         group.setMgroup_introduce(myRs.getString("mgroup_introduce"));
      group.setMgroup_maincategory(myRs.getString("mgroup_maincategory"));
      group.setMgroup_middlecategory(myRs.getString("mgroup_middlecategory"));

         group.setMgroup_local_name(myRs.getString("mgroup_local_name"));
         group.setMgroup_minage(myRs.getInt("mgroup_minage"));
         group.setMgroup_maxage(myRs.getInt("mgroup_maxage"));
         group.setMgroup_gender(myRs.getString("mgroup_gender"));
         group.setMgroup_limit(myRs.getInt("mgroup_limit"));
   
  
             bestGroup.add(group);
          }
          myRs.close();
          mySt.close();
          conn.close();

       } catch (SQLException e) {
          e.printStackTrace();
       }

       return bestGroup;
    }

   @Override
   public void saveUser(UserEntity user){
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.save(user);

   }   
   
   
   @Override
   public MgroupEntity getConnectedGroupInfo(int groupIndex) {

      Session currentSession = sessionFactory.getCurrentSession();
      MgroupEntity theGroupInfo = currentSession.get(MgroupEntity.class, groupIndex);
      return theGroupInfo;
   }
   
   @Override
   public List<UsergroupEntity> getUserRole(int userIndex, int groupIndex) {
      Session currentSession = sessionFactory.getCurrentSession();
      Query<UsergroupEntity> theQuery = currentSession.createQuery("from UsergroupEntity where usergroup_user_index =:usergroup_user_index and usergroup_group_index = :usergroup_group_index", UsergroupEntity.class);
      theQuery.setParameter("usergroup_user_index", userIndex);
      theQuery.setParameter("usergroup_group_index", groupIndex);
      List<UsergroupEntity> theUserRole = theQuery.getResultList();
      return theUserRole;
   }

   @Override
   public List<PostEntity> getConnectedGroupPosts(int groupIndex) {

      List<PostEntity> Posts = new ArrayList<PostEntity>();
      Connection conn = null;
      Statement mySt = null;
      ResultSet myRs = null;

      try {
         conn = dataSource.getConnection();

         String sql = "SELECT * FROM mgroup JOIN usergroup "
               + "ON mgroup.mgroup_index = usergroup.usergroup_group_index " + "AND mgroup_index = " + groupIndex
               + " JOIN post on usergroup.usergroup_index = post.post_usergroup_index"
               + " ORDER BY post_index DESC";
         mySt = conn.createStatement();
         myRs = mySt.executeQuery(sql);
         while (myRs.next()) {

            PostEntity post = new PostEntity();
            post.setPost_index(myRs.getInt("post_index"));
            post.setPost_usergroup_index(myRs.getInt("post_usergroup_index"));
            post.setPost_maintext(myRs.getString("post_maintext"));
            post.setPost_date(myRs.getString("post_date"));
            post.setPost_time(myRs.getString("post_time"));
            post.setPost_like(myRs.getInt("post_like"));
            post.setPost_view(myRs.getInt("post_view"));

            Posts.add(post);
         }
         myRs.close();
         mySt.close();
         conn.close();

      } catch (SQLException e) {
         e.printStackTrace();

      }

      return Posts;

   }

   @Override
   public List<UsergroupEntity> getUserGroup(int userIndex, int mgroupIndex) {
      Session currentSession = sessionFactory.getCurrentSession();
      Query<UsergroupEntity> theQuery = currentSession.createQuery(
            "from UsergroupEntity WHERE usergroup_user_index = :userIndex and usergroup_group_index = :mgroupIndex",
            UsergroupEntity.class);
      theQuery.setParameter("userIndex", userIndex);
      theQuery.setParameter("mgroupIndex", mgroupIndex);
      List<UsergroupEntity> theUsergroupEntity = theQuery.getResultList();
      return theUsergroupEntity;
   }

   @Override
   public void saveWrittenPost(PostEntity bowOfPost) {
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.saveOrUpdate(bowOfPost);
   }

   @Override
   public int findMgroupIndexBase(int getUsergroup_index) {
      Session currentSession = sessionFactory.getCurrentSession();
      UsergroupEntity theUsergroupEntity = currentSession.get(UsergroupEntity.class, getUsergroup_index);
      return theUsergroupEntity.getUsergroup_group_index();
   }

   @Override
   public void saveAuthority(Authorities authorities) {
      String a = authorities.getAuthority();
      String v = authorities.getUsername();
      Connection conn = null;
      Statement mySt = null;

      try {
         conn = dataSource.getConnection();

         String sql = "INSERT INTO authorities(username,authority) values(" + "\'" + v + "\'" + "," + "\'" + a + "\'"
               + ")";
         mySt = conn.createStatement();
         mySt.execute(sql);

      } catch (SQLException e) {
         e.printStackTrace();
      }

   }
   @Override
   public ImgEntity getImg(int img_index) {
      Session currentSession = sessionFactory.getCurrentSession();
      ImgEntity groupMainImage = currentSession.get(ImgEntity.class, img_index);
      return groupMainImage;
   }
   
   @Override
   public void saveGroup(MgroupEntity mgroup) {

      if (mgroup.getMgroup_title().equals("")) {

      }
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.save(mgroup);
   }

   @Override
   public void makeTheLeader(UsergroupEntity usergroupEntity) {
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.save(usergroupEntity);
   }
   
   @Override
   public List<UsergroupUserDto> GroupUserInfo(int mgroupIndex, int userIndex) {
      List<UsergroupUserDto> GroupUsersInfo = new ArrayList<UsergroupUserDto>();
      Connection conn = null;
      Statement mySt = null;
      ResultSet myRs = null;

      try {
         conn = dataSource.getConnection();
         String sql = "SELECT * FROM usergroup JOIN users ON usergroup.usergroup_user_index = users.user_index WHERE usergroup_group_index = " + mgroupIndex;
         if(userIndex > 0) {
            sql = sql + " and usergroup_user_index = " + userIndex;
         }
         mySt = conn.createStatement();
         myRs = mySt.executeQuery(sql);
         while (myRs.next()) {

            UsergroupUserDto GroupUserInfo = new UsergroupUserDto();
            GroupUserInfo.setUsergroup_index(myRs.getInt("usergroup_index"));
            GroupUserInfo.setUsergroup_user_index(myRs.getInt("usergroup_user_index"));
            GroupUserInfo.setUsergroup_group_index(myRs.getInt("usergroup_group_index"));
            GroupUserInfo.setUsergroup_user_role(myRs.getString("usergroup_user_role"));
            GroupUserInfo.setUser_index(myRs.getInt("user_index"));
            
            GroupUserInfo.setUser_name(myRs.getString("user_name"));
            GroupUserInfo.setUser_phone(myRs.getString("user_phone"));
            GroupUserInfo.setUser_birth(myRs.getString("user_birth"));
            GroupUserInfo.setUser_gender(myRs.getString("user_gender"));
            GroupUserInfo.setUsername(myRs.getString("username"));
            
            GroupUserInfo.setPassword(myRs.getString("password"));
            GroupUserInfo.setUser_joinday(myRs.getString("user_joinday"));
            GroupUserInfo.setUser_out(myRs.getInt("user_out"));
            GroupUserInfo.setEnabled(myRs.getInt("enabled"));
            GroupUserInfo.setAuthority(myRs.getString("authority"));
            
            GroupUsersInfo.add(GroupUserInfo);
         }
         myRs.close();
         mySt.close();
         conn.close();

      } catch (SQLException e) {
         e.printStackTrace();
      }

      return GroupUsersInfo;
   }

   @Override
   public List<ImgEntity> getImg() {
      Session currentSession = sessionFactory.getCurrentSession();
      Query<ImgEntity> theQuery = currentSession.createQuery("from ImgEntity", ImgEntity.class);
      List<ImgEntity> Imgs = theQuery.getResultList();
      return Imgs;
   }
   
   @Override
   public void savejoingroup(int userIndex, int mgroupIndex) {

      Connection conn = null;
      Statement mySt = null;

      try {
         conn = dataSource.getConnection();

         String sql = "INSERT INTO usergroup(usergroup_user_index,usergroup_group_index,usergroup_user_role) values("
               + userIndex + "," + mgroupIndex + "," + "'employee')";
         mySt = conn.createStatement();
         mySt.execute(sql);
      } catch (SQLException e) {
         e.printStackTrace();
      }

   }
   
      @Override
      public void updateUserInfo(int user_index, String user_phone, String password) {
         Session currentSession = sessionFactory.getCurrentSession();
         String hql = "UPDATE UserEntity set user_phone = :Suser_phone,"
               + "password = :Spassword"          
               +" WHERE user_index = :Suser_index" ;
         Query theQuery = currentSession.createQuery(hql);
         theQuery.setParameter("Suser_phone", user_phone);   
         theQuery.setParameter("Suser_index", user_index);
         theQuery.setParameter("Spassword", password);
         
      
         
         theQuery.executeUpdate();
         
      }

      @Override
         public void DeleteUser(int user_index) {
            Session currentSession = sessionFactory.getCurrentSession();
            
            String hql = "DELETE FROM UserEntity "  + 
                      "WHERE user_index = :User_id";
            Query theQuery = currentSession.createQuery(hql);
            theQuery.setParameter("User_id",user_index);
            theQuery.executeUpdate();
            
         }
      
      @Override
      public List<UserEntity> theUserInformation(String userId) {
         Session currentSession = sessionFactory.getCurrentSession();
         String hql = "from UserEntity where username = :userId";
         Query<UserEntity> theQuery = currentSession.createQuery(hql, UserEntity.class);
         theQuery.setParameter("userId", userId);
         List<UserEntity> Users = theQuery.getResultList();
         return Users;
      }


      
   
   @Override
   public List<MgroupEntity> searchGroup(String searchGroup) {
      Session currentSession = sessionFactory.getCurrentSession();
      searchGroup = searchGroup.trim().replace(" ", "");
      Query theQuery = null;

      String hql = "select M from MgroupEntity M where M.mgroup_maincategory like :SsearchGroup or M.mgroup_middlecategory like :SsearchGroup or M.mgroup_local_name like :SsearchGroup";

      if (searchGroup != null && searchGroup.length() > 0) {
         theQuery = currentSession.createQuery(hql, MgroupEntity.class);
         theQuery.setParameter("SsearchGroup", "%" + searchGroup + "%");
      } else {
         theQuery = currentSession.createQuery("From MgroupEntity", MgroupEntity.class);
      }
      List<MgroupEntity> searchGroups = theQuery.getResultList();

      return searchGroups;

   }
   
      @Override
      public List<MgroupEntity> getmygroup(int userIndex, String usergroupUserRole) {
         List<MgroupEntity> getmygroup = new ArrayList<MgroupEntity>();
         Connection conn = null;
         Statement mySt = null;
         ResultSet myRs = null;

         try {
            conn = dataSource.getConnection();
            String sql = null;
            if(usergroupUserRole.equals("admin")){
               sql = "SELECT * FROM mgroup join usergroup"
                     +" ON mgroup.mgroup_index = usergroup.usergroup_group_index"
                     +" WHERE usergroup_user_index = "+ userIndex
                     +" and usergroup_user_role = \"admin\"";
            }else if(usergroupUserRole.equals("employee")){
               sql = "SELECT * FROM mgroup join usergroup"
                     +" ON mgroup.mgroup_index = usergroup.usergroup_group_index"
                     +" WHERE usergroup_user_index = "+ userIndex
                     +" and usergroup_user_role = \"employee\" ";
            }else if(usergroupUserRole.equals("normal")){
               sql = "SELECT * FROM mgroup join usergroup"
                     +" ON mgroup.mgroup_index = usergroup.usergroup_group_index"
                     +" WHERE usergroup_user_index = "+ userIndex
                     +" and usergroup_user_role = \"normal\" ";
            }
            mySt = conn.createStatement();
            myRs = mySt.executeQuery(sql);
            while (myRs.next()) {

               MgroupEntity group = new MgroupEntity();
               group.setMgroup_index(myRs.getInt("mgroup_index"));
               group.setMgroup_title(myRs.getString("mgroup_title"));
               group.setMgroup_img(myRs.getInt("mgroup_img"));
               group.setMgroup_img_url(myRs.getString("mgroup_img_url"));
               group.setMgroup_introduce(myRs.getString("mgroup_introduce"));
               group.setMgroup_maincategory(myRs.getString("mgroup_maincategory"));
               group.setMgroup_middlecategory(myRs.getString("mgroup_middlecategory"));

               group.setMgroup_local_name(myRs.getString("mgroup_local_name"));
               group.setMgroup_minage(myRs.getInt("mgroup_minage"));
               group.setMgroup_maxage(myRs.getInt("mgroup_maxage"));
               group.setMgroup_gender(myRs.getString("mgroup_gender"));
               group.setMgroup_limit(myRs.getInt("mgroup_limit"));

               getmygroup.add(group);
            }
            myRs.close();
            mySt.close();
            conn.close();

         } catch (SQLException e) {
            e.printStackTrace();
         }

         return getmygroup;
      }
      
      @Override
      public List<MgroupEntity> randomGroup() {
         Session currentSession = sessionFactory.getCurrentSession();
         Query<MgroupEntity> theQuery = currentSession.createQuery("FROM MgroupEntity", MgroupEntity.class);
         
         List<MgroupEntity> randomGroups = theQuery.getResultList();
         Collections.shuffle(randomGroups);
         int sublistSize = 9;
         
          if (randomGroups.size() < sublistSize) {
             sublistSize = randomGroups.size();
          }
          List<MgroupEntity> ShuffleGroups = new ArrayList<MgroupEntity>();
          ShuffleGroups = randomGroups.subList(0, sublistSize);
//          ShuffleGroups.add(randomGroups.get(0));
//          ShuffleGroups.add(randomGroups.get(1));
//          ShuffleGroups.add(randomGroups.get(2));
//          ShuffleGroups.add(randomGroups.get(3));
//          ShuffleGroups.add(randomGroups.get(4));
//          ShuffleGroups.add(randomGroups.get(5));
//          ShuffleGroups.add(randomGroups.get(6));
//          ShuffleGroups.add(randomGroups.get(7));
//          ShuffleGroups.add(randomGroups.get(8));

         
         return ShuffleGroups;
      }

   @Override
   public UsergroupEntity getUsergroupInfo(int usergroup_index) {
      Session currentSession = sessionFactory.getCurrentSession();
      UsergroupEntity getUsergroupInfo = currentSession.get(UsergroupEntity.class, usergroup_index);
      return getUsergroupInfo;
   }

      
   @Override
   public void nonMemberRegistration(UsergroupEntity usergroupInfo) {
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.update(usergroupInfo);
      
   }

   @Override
   public void exportGroup(UsergroupEntity usergroupInfo) {
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.delete(usergroupInfo);
   }

      @Override
      public void DeleteGroupsAtUserGroup(int mgroupIndex) {
         Session currentSession = sessionFactory.getCurrentSession();
          String hql = "DELETE FROM UsergroupEntity "  + 
                   "WHERE usergroup_group_index = :mgroupIndex";
         Query theQuery = currentSession.createQuery(hql);
         theQuery.setParameter("mgroupIndex",mgroupIndex);
         theQuery.executeUpdate();
         System.out.println("test");
         
      }

      @Override
      public int countMember(int groupIndex) {
         int count = 0;
         String sql = null;
         Connection conn = null;
         Statement mySt = null;
         ResultSet myRs = null;
         System.out.println("test try");
         try {
            System.out.println("test try");
            conn = dataSource.getConnection();
            mySt = conn.createStatement();
            sql = "SELECT count(*) as number FROM moiza.usergroup where usergroup_group_index = "+groupIndex;         
            myRs = mySt.executeQuery(sql);
               while (myRs.next()) {
                  count = myRs.getInt("number");
              
                  }                  
            myRs.close();
            mySt.close();
            conn.close();

         } 
         catch (SQLException e) {
            e.printStackTrace();
         }
         return count;
      }
      @Override
      public void DeleteGroup(int mgroupIndex) {
         Session currentSession = sessionFactory.getCurrentSession();
          String hql = "DELETE FROM MgroupEntity "  + 
                   "WHERE mgroup_index = :mgroupIndex";
         Query theQuery = currentSession.createQuery(hql);
         theQuery.setParameter("mgroupIndex",mgroupIndex);
         theQuery.executeUpdate();
         System.out.println("test");
         
      }

      @Override
      public void pluslike(int like) {
         Connection conn = null;
         Statement mySt = null;
         

         
         try {
            
            conn = dataSource.getConnection();
            mySt = conn.createStatement();
            String sql = "update post set post_like = post_like +1  where post_index =" + like;
            mySt.execute(sql);
            System.out.println("sql");
            
         }catch(SQLException e) {
            e.printStackTrace();
            System.out.println("test1");
         }
         
         
         
      }

   @Override
   public List<MgroupEntity> searchaddress(String address) {
      Session currentSession = sessionFactory.getCurrentSession();
      address = address.trim().replace(" ", "").substring(1, 3);
      Query theQuery = null;

      String hql = "select M from MgroupEntity M where M.mgroup_local_name like :Ssearchaddress";

      if (address != null && address.length() > 0) {
         theQuery = currentSession.createQuery(hql, MgroupEntity.class);
         theQuery.setParameter("Ssearchaddress", "%" + address + "%");
      } else {
         theQuery = currentSession.createQuery("From MgroupEntity", MgroupEntity.class);
      }
      List<MgroupEntity> searchaddress = theQuery.getResultList();
      return searchaddress;
   }
   
   @Override
   public void deletePost(PostEntity deletedPost) {
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.delete(deletedPost);
      
   }

   @Override
   public void saveEditedPost(PostEntity editedPost) {
      Session currentSession = sessionFactory.getCurrentSession();
      currentSession.saveOrUpdate(editedPost);
   }

   @Override
   public boolean IdCheck(String username) {
   
      List<String> Ids = new ArrayList<String>();
      Connection conn = null;
      Statement mySt = null;
      ResultSet myRs = null;

      try {
         conn = dataSource.getConnection();
         String sql = "SELECT username FROM users";

         mySt = conn.createStatement();
         myRs = mySt.executeQuery(sql);
         while (myRs.next()) {
            
            Ids.add(myRs.getString("username"));

         }
         myRs.close();
         mySt.close();
         conn.close();
         
         System.out.println(Ids);
         
         
      } catch (SQLException e) {
         e.printStackTrace();
      }
      boolean a = Ids.contains(username);
      return a;
   }
}