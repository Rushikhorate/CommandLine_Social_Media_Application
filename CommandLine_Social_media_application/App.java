package com.CommandLine_Social_media_application;

import java.net.ConnectException;
import java.util.*;

import com.social.media.application.DAO.SocialMediaApplicationDAOImplementation;
import com.social.media.application.DAO.SocialMediaApplicationDAOInterface;
import com.social.media.application.entity.UserEntities;

/**
 * Hello world!
 *
 */
public class App 
{	
	//Main method
    public static void main( String[] args ) throws Exception
    {
    	
    	SocialMediaApplicationDAOInterface dao =new SocialMediaApplicationDAOImplementation();
    	System.out.println("\n\n--------Welcome to World Famous site-------\n\t\t   @ FaceBook @\n");
    	
    	String flag=null;
    	
    	do {
    		System.out.println("*****************************************");
	    	System.out.println("\n     1. Sign up \n     2. Log in\n");
	    	System.out.println("*****************************************\n");
	    	int choice;
	    	System.out.print("Enter your choice : ");
	    	Scanner sc= new Scanner(System.in);
	    	choice = sc.nextInt();
	    	System.out.println();
	    	
		    	switch(choice)
		    	{
			    	case 1:
			    		dao.signIn();
			    		break;
			    		
			    	case 2:
			    		dao.LogIn();
			    		break;
			    		
			    	default:
			    		System.out.println("Please enter valid choice..\n");
			    		main(null);
			    		
		    	}

	    	
	    	System.out.print("\nDo you want to Sign in / log in  FaceBook (yes/no) : ");
	    	flag = sc.next();
    	}while(flag.equalsIgnoreCase("yes"));
    	
    	System.out.println("\n\n\t\t**THANK YOU VISIT AGAIN!!!**");   	
    	    	
    	
    }
}
