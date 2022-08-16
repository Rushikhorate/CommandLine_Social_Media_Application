package com.social.media.application.DAO;

import java.sql.SQLException;
import java.util.List;

import com.social.media.application.entity.UserEntities;

public interface SocialMediaApplicationDAOInterface {
	
	abstract void insertUserInformation(UserEntities ue) throws SQLException;
	
	void LogIn() throws Exception;
	void signIn() throws Exception;
	
	boolean loginEmailCheckIsAvailable(String loginEmail) throws Exception;
	
	boolean loginEmailPasswordCheckIsAvailable(String loginEmail,String loginpassword) throws Exception;
	
	List<UserEntities> viewProfileofUser(String userName, String userPassword) throws Exception;
	
	List<UserEntities> viewAllProfiles(String email) throws Exception;
	
	void updateUserProfile(String email) throws Exception;
	void updateUserByName(String email,String name) throws Exception;
	void updateUserByAddress(String email,String address) throws Exception;
	void updateUserByAge(String email,String age) throws Exception;
	void updateUserByGender(String email,String gender) throws Exception;
	
	void deleteProfile(String email) throws Exception;
	
	List<UserEntities> searchProfile(String name) throws Exception;
	
	void createPost(String email) throws Exception;
	
	List<UserEntities> showTimeline(String email) throws Exception;
	
	List<UserEntities> seeAllPostCrwatedByUsers() throws Exception;
	
	void likeDislikeOptions() throws Exception;
	void likeDislike(int id) throws Exception;
	void like(int id) throws Exception;
	void dislike(int id) throws Exception;
}
