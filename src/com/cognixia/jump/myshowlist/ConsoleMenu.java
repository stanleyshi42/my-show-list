package com.cognixia.jump.myshowlist;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
	private DAOClass db = new DAOClass();
	private int sessionID = -1;	// Tracks which user is currently logged in
	
	public void welcomeMenu() {
		Scanner sc = new Scanner(System.in);
		
		while(true){
			try {
				System.out.println("==Welcome to myShowList!==");
				System.out.println("Enter an option:");
				System.out.println("[1] Login");
				System.out.println("[2] Register");
				System.out.println("[3] Exit Program");
				
				int userInput = sc.nextInt();
				switch (userInput) {
				case 1:
					loginMenu(sc);
					break;
				case 2:
					System.out.println("Call registerMenu()"); //TODO
					break;
				case 3:
					System.out.println("Exiting program");
					System.exit(0);
					break;
				default:
					throw new java.util.InputMismatchException();
				}
			} 
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid option");
				sc.nextLine();
	        }
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loginMenu(Scanner sc) {
		sc.nextLine();	// Clear scanner buffer
		while(true){
			try {
				System.out.println("==Login==");
				System.out.println("Enter your username, or hit [Enter] to go back:");
				String username=sc.nextLine();
				if(username.isEmpty()) {
					return;	// Go back to welcomeMenu
				}
				System.out.println("Enter your password:");
				String password=sc.nextLine();
				
				User user = login(username, password);
				// If login was successful, log the user in
				if(user != null) {
					sessionID = user.getUser_id();
					userMenu(sc);
				}
				else {
					System.out.println("Invalid username/password");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private User login(String username, String password) {
		User user = db.getUserByUsername(username);
		// If login credentials are valid, return the user
		if(user != null && password.equals(user.getPassword())) {
			return user;
		}
		// Else, return null
		return null;
	}
	
	private void userMenu(Scanner sc) {
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Progress Trackers==");
		System.out.format("%33s%10s%10s%14s", "Show", "Episodes",
				"Seasons", "Status");
		for(Tracker t : trackers) {
			System.out.println();
			System.out.format("%33s%10d%10d%14s", db.getShowById(t.getShowID()).getTitle(), t.getCurrentEpisode(),
					t.getCurrentSeason(), db.getStatus(t.getStatusID()));
		}
		
		while(true){
			try {
				System.out.println();
				System.out.println("Enter an option:");
				System.out.println("[1] Update a tracker");
				System.out.println("[2] Add a tracker");
				System.out.println("[3] Delete a tracker");
				System.out.println("[4] Logout");
				
				int userInput=sc.nextInt();
				switch (userInput) {
				case 1:
					//TODO
					break;
				case 2:
					//TODO
					break;
				case 3:
					//TODO
					break;
				case 4:
					sessionID = -1;	// Resets current user
					return;	// Go back to loginMenu
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
