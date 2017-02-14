/**
 * 
 */
package fr.tbr.iamcoresrikanth.launcher;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.tbr.iamcoresrikanth.datamodel.Identity;
import fr.tbr.iamcoresrikanth.exception.DAODeleteException;
import fr.tbr.iamcoresrikanth.exception.DAOInitializationException;
import fr.tbr.iamcoresrikanth.exception.DAOUpdateException;
import fr.tbr.iamcoresrikanth.service.authentication.AuthenticationService;
import fr.tbr.iamcoresrikanth.service.dao.DerbyOperations;

/**
 *
 *
 */
public class Application {
	
	private static final Logger logger =  LogManager.getLogger(Application.class);

	 public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, DAOInitializationException, DAOUpdateException, DAODeleteException {
		logger.info("started");
		DerbyOperations.createConnection();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the IAM System");

		System.out.println("Please enter your user name: ");
		String username = scanner.nextLine();
		System.out.println("Please enter your password: ");
		String password = scanner.nextLine();

		AuthenticationService authService = new AuthenticationService();
		if (!authService.authenticate(username, password)) {
			System.out.println("The provided credentials are wrong");
			scanner.close();
			return;
		}
		System.out.println("Authentication Successfull");
		System.out.println("Welcome to IAM System");
		
		int ch;
		do{
		System.out.println("Menu for the IAM application :");
		System.out.println("A - Create an Identity");
		System.out.println("B - Update an Identity");
		System.out.println("C - Delete an Identity");
                System.out.println("D - Exit");
		System.out.print("your choice (A|B|C|D) : ");
		String menuAnswer = scanner.nextLine();
		switch (menuAnswer) {
		case "A":
			System.out.println("Creation Activity");

			System.out.print("Please enter the Identity display name");
			String displayName = scanner.nextLine();
			
			System.out.print("Please enter the Identity email address");
			String email = scanner.nextLine();
			
			
			String uid = "A";
			
			Identity identity = new Identity(displayName, email, uid);

			try {
				
				DerbyOperations.insertIdentity(identity);
				System.out.print("Identity created");
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			break;
		case "B":
			//Propose a search activity in order to select the identity to modify
			System.out.println("Modification Activity");
			
			
			if(DerbyOperations.selectIdentities())
			{
				System.out.print("Please enter the Identity uid to update");
				String uid1 = scanner.nextLine();
				System.out.print("Please enter the Identity display name");
				String displayName1 = scanner.nextLine();
				System.out.print("Please enter the Identity email address");
				String email1 = scanner.nextLine();
				Identity identity1 = new Identity(displayName1, email1, uid1);
				if(DerbyOperations.updateIdentity(identity1))
					System.out.println("Identity updated successfully.");
				else
					System.out.println("Error, please try again later.");
			}else
				System.out.println("There no identity found for updation.");
			
			break;

		case "C":
			//Propose a search activity in order to select the identity to delete
			System.out.println("Deletion Activity");
			
			
			if(DerbyOperations.selectIdentities())
			{
				System.out.print("Please enter the Identity uid to delete");
				String uid2 = scanner.nextLine();
				
				if(DerbyOperations.deleteIdentity(uid2))
					System.out.println("Identity deleted successfully.");
				else
					System.out.println("Error, please try again later.");
			}else
				System.out.println("There is no identity found for deletion.");
			
			
			break;
                case "D":
			System.out.println("The program will now exit");
			scanner.close(); 
			return;

		default:
			break;
		}
			System.out.println("\nPress 1 to continue the menu\t:");
			Scanner sc=new Scanner(System.in);
			ch=sc.nextInt();
		}while(ch==1);
		logger.info("Program closed");
		DerbyOperations.shutdown();
		scanner.close();
		
	}

}
