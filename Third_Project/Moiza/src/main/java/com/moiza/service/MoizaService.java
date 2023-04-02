package com.moiza.service;

import java.util.List;

import com.moiza.entity.UsergroupEntity;
import com.moiza.dto.UsergroupUserDto;
import com.moiza.entity.Authorities;
import com.moiza.entity.ImgEntity;

import com.moiza.entity.MgroupEntity;
import com.moiza.entity.PostEntity;
import com.moiza.entity.UserEntity;

public interface MoizaService {

	int UseridChangeUserindex(String userId);

	List<MgroupEntity> getSubscribedMgroup(int userIndex);
	
	List<MgroupEntity> bestGroup();

	public void saveUser(UserEntity user);

	MgroupEntity getConnectedGroupInfo(int groupIndex);
	
	List<UsergroupEntity> getUserRole(int userIndex, int groupIndex);

	List<PostEntity> getConnectedGroupPosts(int groupIndex);

	List<UsergroupEntity> getUserGroup(int userIndex, int mgroupIndex);

	void saveWrittenPost(PostEntity bowOfPost);

	int findMgroupIndexBase(int getUsergroup_index);

	void saveAuthority(Authorities authorities);

	void saveGroup(MgroupEntity mgroup);

	void makeTheLeader(UsergroupEntity usergroupEntity);
	
	List<UsergroupUserDto> GroupUserInfo(int mgroupIndex, int userIndex);

	List<ImgEntity> getImg();

	void savejoingroup(int userIndex, int mgroupIndex);

	List<UserEntity> theUserInformation(String userId);

	void updateUserInfo(int user_index, String user_phone, String password);

	void DeleteUser(int user_index);


	List<MgroupEntity> searchGroup(String searchGroup);

	List<MgroupEntity> getmygroup(int userIndex, String string);

	List<MgroupEntity> randomGroup();
	
	UsergroupEntity getUsergroupInfo(int usergroup_index);

	void nonMemberRegistration(UsergroupEntity usergroupInfo);

	void exportGroup(UsergroupEntity usergroupInfo);

	void DeleteGroupsAtUserGroup(int mgroupIndex);

	int countMember(int groupIndex);
	void DeleteGroup(int mgroupIndex);

	void pluslike(int like);

	List<MgroupEntity> searchaddress(String address);
	void deletePost(PostEntity deletedPost);

	void saveEditedPost(PostEntity editedPost);
	boolean IdCheck(String username);
	ImgEntity getImg(int img_index);

	
	
}
