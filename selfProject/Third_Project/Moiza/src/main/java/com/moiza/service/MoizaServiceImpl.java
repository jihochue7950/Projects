package com.moiza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moiza.entity.Authorities;
import com.moiza.entity.ImgEntity;

import com.moiza.entity.MgroupEntity;
import com.moiza.entity.PostEntity;
import com.moiza.entity.UserEntity;
import com.moiza.dao.MoizaDao;
import com.moiza.dto.UsergroupUserDto;
import com.moiza.entity.UsergroupEntity;

@Service
public class MoizaServiceImpl implements MoizaService {

	@Autowired
	private MoizaDao moizaDao;

	@Override
	@Transactional
	public int UseridChangeUserindex(String userId) {
		return moizaDao.UseridChangeUserindex(userId);
	}

	@Override
	@Transactional
	public List<MgroupEntity> getSubscribedMgroup(int userIndex) {
		return moizaDao.getSubscribedMgroup(userIndex);
	}

	@Override
	@Transactional
	public List<MgroupEntity> bestGroup() {

		return moizaDao.bestGroup();
	}

	@Override
	@Transactional
	public void saveUser(UserEntity user) {
		moizaDao.saveUser(user);

	}

	@Override
	@Transactional
	public MgroupEntity getConnectedGroupInfo(int groupIndex) {
		return moizaDao.getConnectedGroupInfo(groupIndex);
	}

	@Override
	@Transactional
	public List<UsergroupEntity> getUserRole(int userIndex, int groupIndex) {
		return moizaDao.getUserRole(userIndex, groupIndex);
	}

	@Override
	@Transactional
	public List<PostEntity> getConnectedGroupPosts(int groupIndex) {
		return moizaDao.getConnectedGroupPosts(groupIndex);
	}

	@Override
	@Transactional
	public List<UsergroupEntity> getUserGroup(int userIndex, int mgroupIndex) {
		return moizaDao.getUserGroup(userIndex, mgroupIndex);
	}

	@Override
	@Transactional
	public void saveWrittenPost(PostEntity bowOfPost) {
		moizaDao.saveWrittenPost(bowOfPost);
	}

	@Override
	@Transactional
	public int findMgroupIndexBase(int getUsergroup_index) {
		return moizaDao.findMgroupIndexBase(getUsergroup_index);

	}

	@Override
	@Transactional
	public void saveAuthority(Authorities authorities) {
		moizaDao.saveAuthority(authorities);

	}

	@Override
	@Transactional
	public void saveGroup(MgroupEntity mgroup) {
		moizaDao.saveGroup(mgroup);
	}

	@Override
	@Transactional
	public void makeTheLeader(UsergroupEntity usergroupEntity) {
		moizaDao.makeTheLeader(usergroupEntity);

	}

	@Override
	@Transactional
	public List<UsergroupUserDto> GroupUserInfo(int mgroupIndex, int userIndex) {
		return moizaDao.GroupUserInfo(mgroupIndex, userIndex);
	}

	@Override
	@Transactional
	public List<ImgEntity> getImg() {
		return moizaDao.getImg();
	}

	@Override
	@Transactional
	public void savejoingroup(int userIndex, int mgroupIndex) {
		moizaDao.savejoingroup(userIndex, mgroupIndex);

	}

	@Override
	@Transactional
	public List<UserEntity> theUserInformation(String userId) {
		return moizaDao.theUserInformation(userId);
	}

	@Override
	@Transactional
	public void updateUserInfo(int user_index, String user_phone, String password) {
		moizaDao.updateUserInfo(user_index, user_phone, password);

	}

	@Override
	@Transactional
	public void DeleteUser(int user_index) {
		moizaDao.DeleteUser(user_index);
	}

	

	@Override
	@Transactional
	public List<MgroupEntity> searchGroup(String searchGroup) {
		return moizaDao.searchGroup(searchGroup);
	}

	@Override
	@Transactional
	public List<MgroupEntity> getmygroup(int userIndex, String string) {
		return moizaDao.getmygroup(userIndex, string);
	}

	@Override
	@Transactional
	public List<MgroupEntity> randomGroup() {
		return moizaDao.randomGroup();
	}
	
	@Override
	@Transactional
	public UsergroupEntity getUsergroupInfo(int usergroup_index) {
		return moizaDao.getUsergroupInfo(usergroup_index);
	}


	@Override
	@Transactional
	public void nonMemberRegistration(UsergroupEntity usergroupInfo) {
		moizaDao.nonMemberRegistration(usergroupInfo);
	}

	@Override
	@Transactional
	public void exportGroup(UsergroupEntity usergroupInfo) {
		moizaDao.exportGroup(usergroupInfo);
	}

	@Override
	@Transactional
	public void DeleteGroupsAtUserGroup(int mgroupIndex) {
		moizaDao.DeleteGroupsAtUserGroup(mgroupIndex);
		
	}

	@Override
	@Transactional
	public int countMember(int groupIndex) {
		// TODO Auto-generated method stub
		return moizaDao.countMember(groupIndex);
	}

	@Override
	@Transactional
	public void DeleteGroup(int mgroupIndex) {
		moizaDao.DeleteGroup(mgroupIndex);
	}

	@Override
	@Transactional
	public void pluslike(int like) {
		moizaDao.pluslike(like);
	}

	@Override
	@Transactional
	public List<MgroupEntity> searchaddress(String address) {
		// TODO Auto-generated method stub
		return moizaDao.searchaddress(address);
	}

	@Override

	@Transactional
	public void deletePost(PostEntity deletedPost) {
		moizaDao.deletePost(deletedPost);
		
	}

	@Override
	@Transactional
	public void saveEditedPost(PostEntity editedPost) {
		moizaDao.saveEditedPost(editedPost);
		
	}

	@Override
	@Transactional
	public boolean IdCheck(String username) {
		// TODO Auto-generated method stub
		return moizaDao.IdCheck(username);
	}

	@Override
	@Transactional
	public ImgEntity getImg(int img_index) {
		// TODO Auto-generated method stub
		return moizaDao.getImg(img_index);
	}

	

}
