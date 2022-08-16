package com.social.media.application.DAO;

import java.lang.Thread.State;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.CommandLine_Social_media_application.App;
import com.social.media.application.entity.UserEntities;
import com.social.media.application.util.Connection_Util;

public class SocialMediaApplicationDAOImplementation implements SocialMediaApplicationDAOInterface{
	
	Scanner sc = new Scanner(System.in);
	
	//Declared Connection as private
	private static Connection con;
	
	//this method is for connection and it is declared global 
	static {
		try {
			con =Connection_Util.get_Connection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//method for printing the user information
	static void Print(UserEntities s)
	{
//		int id=s.getUserID();
		String name =s.getUserName();
		String addr = s.getUserAddress();
		int age = s.getUserAge();
		String gender = s.getUserGender();
		
		
		System.out.println(" Name    :  "+name+"\n Address :  "+addr+"\n Age     :  "+age+"\n Gender  :  "+gender+"\n");
		System.out.println("*****************************************\n");
	}
	
	
	//method for sign in
	public void signIn(){
		
		try {
		
				SocialMediaApplicationDAOInterface dao =new SocialMediaApplicationDAOImplementation();
				Scanner sc = new Scanner(System.in);
				
				String insert_username;
				System.out.print("Enter the name : ");
				insert_username = sc.nextLine();
				
				String insert_useraddress;
				System.out.print("\nEnter the Address : ");
				insert_useraddress = sc.nextLine();
				
				int insert_age;
				System.out.print("\nEnter your age : ");
				insert_age = sc.nextInt();
				
				String insert_gender;
				System.out.print("\nEnter your gender : ");
				insert_gender = sc.next();
				
				String insert_email;
				System.out.print("\nEnter the Email : ");
				insert_email = sc.next();
				sc.nextLine();		
				
				String insert_password;
				System.out.print("\nEnter the password : ");
				insert_password = sc.next();
				
				
				UserEntities ue = new UserEntities();
				ue.setUserEmail(insert_email);
				ue.setUserName(insert_username);
				ue.setUserAddress(insert_useraddress);
				ue.setUserAge(insert_age);
				ue.setUserGender(insert_gender);
				ue.setUserPassword(insert_password);
				
				dao.insertUserInformation(ue);
				System.out.println("\t!!!!Hurray!!!!");
		
		}
		
		catch(SQLIntegrityConstraintViolationException e)
		{
			System.err.println("<|<| Alert : This email is already present in database |>|>");
		}
		catch(Exception e)
		{System.out.println("eee");
			e.printStackTrace();
		}
		
	}
	
	//Method for Log in
	public void LogIn() throws Exception {
		
		SocialMediaApplicationDAOInterface dao =new SocialMediaApplicationDAOImplementation();

		
		boolean choice1 = true;
		boolean choice2 = true;
		String login_email = null;	
		String login_password=null;
		    		
		while(choice1)
		{
			System.out.print("Enter Your Email : ");
			login_email = sc.next();
			choice1=dao.loginEmailCheckIsAvailable(login_email);
			if(choice1==true)
			{
				System.err.println("Warning --> No such email is present in database, please re-enter the email");
			}
		}
		    		
		while(choice2)
		{
			System.out.print("\nEnter the password : ");
    		login_password = sc.next();		    			
    		choice2 = dao.loginEmailPasswordCheckIsAvailable(login_email, login_password);
    		if(choice2==true)
			{
				System.err.println("Warning --> Password is incorrect, please re-enter the password..");
			}
		}
		    		
		System.out.println();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
		System.out.println(":::::::::: You are Login as --> "+login_email+" ::::::::::\n");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
		
		String flag;
		boolean flag1=false;
		
		do {
				System.out.println("\n\t\t\t--------MENU--------\n");
				System.out.println("\t\t\t1. View Profile\n"
						+ "\t\t\t2. View All Profile\n"
						+ "\t\t\t3. Update Profile\n"
						+ "\t\t\t4. Delete Profile\n"
						+ "\t\t\t5. Search Profile\n"
						+ "\t\t\t6. Create a Post\n"
						+ "\t\t\t7. Show Timeline\n"
						+ "\t\t\t8. See post created by other users\n"
						+ "\t\t\t9. Log Out\n");
				
				System.out.print("Enter your choice : ");
				int choice = sc.nextInt();
				System.out.println();
				
				switch(choice)
				{
				case 1:
					System.out.println("\n*****************************************\n");
					List<UserEntities> list = dao.viewProfileofUser(login_email, login_password);
					for(UserEntities e : list)
					{
						Print(e);
					}			
					break;
					
				case 2:
					System.out.println("\n*****************************************"+"\n");
					List<UserEntities> allist = dao.viewAllProfiles(login_email);
					for(UserEntities e : allist)
					{
						Print(e);
					}
					break;
					
				case 3:
					dao.updateUserProfile(login_email);
					break;
					
				case 4:
					dao.deleteProfile(login_email);
					App.main(null);
					break;
					
				case 5:
					System.out.println("\nEnter the name : ");
					String name1 = sc.next();
					System.out.println("\n*****************************************\n");
					
					List<UserEntities> slist = dao.searchProfile(name1);
					for(UserEntities e: slist)
					{
						Print(e);
					}
					
					break;
					
				case 6:
					dao.createPost(login_email);
					System.out.println();
					break;
					
				case 7:
					List<UserEntities> show_list = dao.showTimeline(login_email);
					
					if(show_list.isEmpty())
					{
						System.err.println("No posts are Posted before\n");
					}
					else 
					{
						System.out.println("*****************************************");
						for(UserEntities e : show_list)
						{
							PrintPost(e);
						}
					}
					break;
					
				case 8:
					List<UserEntities> showAllPost = dao.seeAllPostCrwatedByUsers();
					if(showAllPost.isEmpty())
					{
						System.err.println("No posts are there before\n");
					}
					else
					{
						System.out.println("*****************************************\n");
						for(UserEntities e : showAllPost)
						{
							PrintAllPost(e);
						}
						dao.likeDislikeOptions();					
					}
					
					break;
					
				case 9:
					System.out.println("#######################################");
					System.out.print("\n Log out in process please wait ");
					for(int i=0;i<5;i++)
					{	
						System.out.print(". ");
						Thread.sleep(1000);
					}
					System.out.println("\n\n <-- You are successfully Log out -->\n");
					System.out.println("#######################################");
					flag1=true;
					break;
				
					
				default:
					System.out.println("Please enter the correct choice..");
				}
			
			if(flag1)
			{
				break;
			}
				
			System.out.print("\nDo u want to see menu again (yes/no) : ");
			flag = sc.next();
			
		}while(flag.equalsIgnoreCase("yes"));	    		
		    		
	}
	
	
	
	//	Method for inserting the data of user in the database
	public void insertUserInformation(UserEntities ue) throws SQLException {
			
			PreparedStatement ps = con.prepareStatement("insert into user_details(user_email, user_name, user_address, user_age, user_gender, user_password) values (?,?,?,?,?,?)");
			
			ps.setString(1, ue.getUserEmail());
			ps.setString(2, ue.getUserName());
			ps.setString(3, ue.getUserAddress());
			ps.setInt(4, ue.getUserAge());
			ps.setString(5, ue.getUserGender());
			ps.setString(6,ue.getUserPassword());
			
			System.out.println();
			if(ps.executeUpdate()==1)
			{
				System.out.println("Profile created Successfully....");
			}
		
	}

	
	
	//Method for email is present in database or not
	public boolean loginEmailCheckIsAvailable(String loginEmail) throws Exception {
		
		Statement stmt =con.createStatement();
		String querycheck = "select user_email from user_details where user_email = '"+loginEmail+"'";		
		ResultSet rs= stmt.executeQuery(querycheck);
		
		if(rs.next())
		{			
			return false;
		}
		
		return true;
	}

	
	
	//Method for email and password is present in database or not
	public boolean loginEmailPasswordCheckIsAvailable(String loginEmail,String loginpassword) throws Exception {
		
		Statement stmt = con.createStatement();
		String querycheck = "select user_email,user_password from user_details where user_email = '"+loginEmail+"'and user_password='"+loginpassword+"'" ;
		ResultSet rs = stmt.executeQuery(querycheck);
		
		if(rs.next())
		{
			return false;
		}
		
		return true;
	}

	
	
	//Method for showing  profile of login user
	public List<UserEntities> viewProfileofUser(String userName, String userPassword) throws Exception {
		
		List<UserEntities> slist = new ArrayList<UserEntities>();
		Statement stmt = con.createStatement();
		String querycheck = "select user_id,user_name,user_address,user_age,user_gender from user_details where user_email='"+userName+"' and user_password ='"+userPassword+"'";
		ResultSet rs =stmt.executeQuery(querycheck);
		
		while(rs.next())
		{
			int id=rs.getInt(1);
			String name = rs.getString(2);
			String addr = rs.getString(3);
			int age = rs.getInt(4);
			String gender = rs.getString(5);
			
			UserEntities ue = new UserEntities();
			ue.setUserID(id);
			ue.setUserName(name);
			ue.setUserAddress(addr);
			ue.setUserAge(age);
			ue.setUserGender(gender);
			
			slist.add(ue);
			
		}
		
		return slist;
	}

	
	

	//Method for listing all profile in the application
	public List<UserEntities> viewAllProfiles(String email) throws Exception {
		
		List<UserEntities> all_list =new ArrayList<UserEntities>();
		
		Statement stmt = con.createStatement();
		String querycheck = "select user_id,user_name,user_address,user_age,user_gender from user_details where user_email != '"+email+"'";
		ResultSet rs = stmt.executeQuery(querycheck);
		
		while(rs.next())
		{
			int id=rs.getInt(1);
			String name = rs.getString(2);
			String addr = rs.getString(3);
			int age = rs.getInt(4);
			String gender = rs.getString(5);
			
			UserEntities ue = new UserEntities();
			ue.setUserID(id);
			ue.setUserName(name);
			ue.setUserAddress(addr);
			ue.setUserAge(age);
			ue.setUserGender(gender);
			
			all_list.add(ue);
		}
		
		return all_list;
	}

	
	
	//Method for updating Name, Address, Age and gender of login user
	public void updateUserProfile(String email) throws Exception {
		
		System.out.println("What do you want to update \n1. Name\n2. Address\n3. Age\n4. Gender\n");
		int choiceToUpdate = sc.nextInt();
		SocialMediaApplicationDAOInterface dao =new SocialMediaApplicationDAOImplementation();
		
		switch(choiceToUpdate)
		{
			case 1:
				System.out.println("Enter the name to update : ");
				sc.nextLine();
				String name1 = sc.nextLine();
				dao.updateUserByName(email,name1);
				break;
				
			case 2:
				System.out.println("Enter the address to update : ");
				sc.nextLine();
				String address = sc.nextLine();
				dao.updateUserByAddress(email, address);
				break;
				
			case 3:
				System.out.println("Enter the age to update : ");
				sc.nextLine();
				String age = sc.nextLine();
				dao.updateUserByAge(email, age);
				break;
				
			case 4:
				System.out.println("Enter the gender to update : ");
				sc.nextLine();
				String gender = sc.nextLine();
				dao.updateUserByGender(email, gender);
				break;
			
				default:
					System.out.println("Please enter the correct choice..\n");
					dao.updateUserProfile(email);
		}
		
	}

	
	
	//Method for update Name
	public void updateUserByName(String email,String name) throws Exception {		
		
		PreparedStatement ps = con.prepareStatement("update user_details set user_name= ? where user_email = ?");
		ps.setString(1, name);
		ps.setString(2, email);
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("Name Updated Successfully\n");
		}
		
	}

	
	
	//Method for update Address
	public void updateUserByAddress(String email, String address) throws Exception {
		PreparedStatement ps = con.prepareStatement("update user_details set user_address= ? where user_email = ? ");
		ps.setString(1, address);
		ps.setString(2, email);
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("Address Updated Successfully\n");
		}
		
	}

	
	
	//Method for update Age
	public void updateUserByAge(String email, String age) throws Exception {
		PreparedStatement ps = con.prepareStatement("update user_details set user_age= ? where user_email = ?");
		ps.setString(1, age);
		ps.setString(2, email);
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("Age Updated Successfully\n");
		}
		
	}

	
	
	//Method for update Gender
	public void updateUserByGender(String email, String gender) throws Exception {
		PreparedStatement ps = con.prepareStatement("update user_details set user_gender= ? where user_email = ?");
		ps.setString(1, gender);
		ps.setString(2, email);
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("Gender Updated Successfully\n");
		}
		
	}

	
	
	//Method for delete the profile of log in  user
	public void deleteProfile(String email) throws Exception {
		
		PreparedStatement ps = con.prepareStatement("delete from user_details where user_email=?");
		
		ps.setString(1, email);
		
		if(ps.executeUpdate()==1)
		{
			System.err.println("Profile deleted Successfully");
		}
		
	}

	
	
	//Method for printing users in the application which is given the name from user
	public List<UserEntities> searchProfile(String name) throws Exception {
		
		List<UserEntities> user_list = new ArrayList<UserEntities>();
		
		Statement stmt = con.createStatement();
		String querycheck = "select user_id, user_name, user_address,user_age,user_gender from user_details where user_name like '"+name+"%' order by user_id asc ";
		ResultSet rs = stmt.executeQuery(querycheck);
		
		while(rs.next())
		{
			int id = rs.getInt(1);
			String name1 = rs.getString(2);
			String add = rs.getString(3);
			int age = rs.getInt(4);
			String gen = rs.getString(5);
			
			UserEntities ue = new UserEntities();
			ue.setUserID(id);
			ue.setUserName(name1);
			ue.setUserAddress(add);
			ue.setUserAge(age);
			ue.setUserGender(gen);
			
			user_list.add(ue);
		}
		
		return user_list;
	}

	
	
	//Method for creating post of login user
	public void createPost(String email) throws Exception {
		
		String message;
		System.out.print("Enter the message to Post : ");
		message = sc.nextLine();
		
		java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String sqlTime = sdf.format(cal.getTime());
		
		
		PreparedStatement ps = con.prepareStatement("insert into user_post(post_message, user_user_email, post_time, post_date) values(?,?,?,?)");
		ps.setString(1, message);
		ps.setNString(2, email);
		ps.setString(3, sqlTime);
		ps.setDate(4, sqlDate);
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("\nPost created Successfully");
		}
		
	}
	
	
	
	//Method for printing Post information
	static void PrintPost(UserEntities e)
	{
		String msg = e.getUserPostMessage();
		String date = e.getUserPostDate();
		String time = e.getUserPostTime();
		int like = e.getUserPostLike();
		int dislike = e.getUserPostDislike();
		
		System.out.println("\nMessage :  "+msg+"\nDate    :  "+date+"\nTime    :  "+time+" \n\nLike    :  "+like+" \tDisLike :  "+dislike+"\n");
		System.out.println("*****************************************");
		
	}

	
	
	//Method for showing all post posted by the login user
	public List<UserEntities> showTimeline(String email) throws Exception {
		
		List<UserEntities> list =new ArrayList<UserEntities>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select post_message,post_date,post_time,post_like,post_dislike from user_post where user_user_email = '"+email+"' order by post_id asc");
		
		while(rs.next())
		{
			String msg = rs.getString(1);
			String date = rs.getString(2);
			int like = rs.getInt(4);
			String time = rs.getString(3);
			int dislike = rs.getInt(5);
			
			UserEntities ue = new UserEntities();
			ue.setUserPostMessage(msg);
			ue.setUserPostDate(date);
			ue.setUserPostLike(like);
			ue.setUserPostTime(time);
			ue.setUserPostDislike(dislike);
			
			list.add(ue);
		}
		
		return list;
		
	}

	
	
	//Method for showing all post posted by the all the users
	static void PrintAllPost(UserEntities u)
	{
		int id = u.getUserPostID();
		String name = u.getUserName();
		String msg = u.getUserPostMessage();
		String time = u.getUserPostTime();
		String date = u.getUserPostDate();
		int like = u.getUserPostLike();
		int dislike = u.getUserPostDislike();
		
		System.out.println("Post number :  "+id+"\nName        :  "+name+"\nMessage     :  "+msg + "\nDate        :  "+date+"\nTime        :  "+time +"\nLike        :  "+like+"\nDislike     :  "+dislike);
		System.out.println("\n*****************************************");
		
	}

	
	
	//Method for showing all post posted by the login user
	public List<UserEntities> seeAllPostCrwatedByUsers() throws Exception {
		
		List<UserEntities> list = new ArrayList<UserEntities>();
		Statement stmt = con.createStatement();
		String querycheck = "select post_id, user_name , post_message, post_time, post_date, post_like, post_dislike from user_details u inner join user_post p on u.user_email = p.user_user_email order by post_id asc";
		ResultSet rs = stmt.executeQuery(querycheck);
		
		while(rs.next())
		{
			int id = rs.getInt(1);
			String name = rs.getString(2);
			int like =rs.getInt(6);
			String msg = rs.getString(3);
			int dislike = rs.getInt(7);
			String time = rs.getString(4);
			String date = rs.getString(5);
			
			UserEntities ue = new UserEntities();
			
			ue.setUserPostID(id);
			ue.setUserName(name);
			ue.setUserPostMessage(msg);
			ue.setUserPostDate(date);
			ue.setUserPostTime(time);
			ue.setUserPostLike(like);
			ue.setUserPostDislike(dislike);
			
			list.add(ue);
		}
		
		return list;
	}


	
	//Method for Like or Dislike
	public void likeDislikeOptions() throws Exception {
		
		SocialMediaApplicationDAOInterface dao =new SocialMediaApplicationDAOImplementation();

		
		String input;
		System.out.print("\nDo you want to like/dislike any post (yes/no) : \n");
		input = sc.next();
		
		if(input.equalsIgnoreCase("yes"))
		{
			System.out.print("\nEnter the post number to like/dislike : ");
			int id;
			id = sc.nextInt();
			dao.likeDislike(id);						
		}
		
	}

	
	
	//Method for Like or Dislike
	public void likeDislike(int id) throws Exception {
		
		int choice;
		System.out.println("\n\t1. Like\t 2. Dislike ");
		choice = sc.nextInt();
		
		switch(choice)
		{
		case 1:
			like(id);
			break;
			
		case 2:
			dislike(id);
			break;
		}
		
	}

	
	
	//Method for Like
	public void like(int id) throws Exception {
		
		PreparedStatement ps = con.prepareStatement("update user_post set post_like = post_like+1 where post_id = '"+id+"'");
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("Liked Successfully");
		}
	}

	
	
	//Method for Dislike
	public void dislike(int id) throws Exception {
		
		PreparedStatement ps = con.prepareStatement("update user_post set post_dislike = post_dislike+1 where post_id = '"+id+"'");
		
		if(ps.executeUpdate()==1)
		{
			System.out.println("disliked Successfully");
		}
		
	}
	
}
