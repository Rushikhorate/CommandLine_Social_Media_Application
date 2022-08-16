package com.social.media.application.util;

import java.io.FileNotFoundException;  
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connection_Util{
	
	private static Connection con=null;	
	private Connection_Util() {	}
	
	public static Connection get_Connection() throws FileNotFoundException, IOException, SQLException
	{
		Properties prop = new Properties();
		prop.load(new FileReader("C:\\Users\\ADMIN\\eclipse-workspace\\Foundational_project_Social_media_application\\src\\main\\resources\\application.properties"));
		
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		
		if(con==null)
		{
			con = DriverManager.getConnection(url,username,password);
		}
		
//		System.out.println("Connection object hashcode"+con.hashCode());
		
		
		return con;
		
	}
}