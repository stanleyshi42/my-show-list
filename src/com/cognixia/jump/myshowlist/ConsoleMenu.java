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
				sc.nextLine();
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
				sc.nextLine();
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
	
	private void printTrackers(List<Tracker> trackers) {
		System.out.println("==Progress Trackers==");
		System.out.format("%35s%10s%10s%15s", "Show", "Episodes",
				"Seasons", "Status");
		for(Tracker t : trackers) {
			System.out.println();
			System.out.format("%35s%10d%10d%15s", db.getShowById(t.getShowID()).getTitle(), t.getCurrentEpisode(),
					t.getCurrentSeason(), db.getStatus(t.getStatusID()));
		}
		System.out.println();
		System.out.println();
	}
	
	private void userMenu(Scanner sc) {
		// Get and print a user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Progress Trackers==");
		System.out.format("%35s%10s%10s%15s", "Show", "Episodes",
				"Seasons", "Status");
		for(Tracker t : trackers) {
			System.out.println();
			System.out.format("%35s%10d%10d%15s", db.getShowById(t.getShowID()).getTitle(), t.getCurrentEpisode(),
					t.getCurrentSeason(), db.getStatus(t.getStatusID()));
		}
		System.out.println();
		System.out.println();
		
		while(true){
			try {
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
					deleteMenu(sc);
					
					break;
				case 4:
					sessionID = -1;	// Resets current user
					return;	// Go back to loginMenu
				}
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid option");
				sc.nextLine();
	        } catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();	// Clear scanner buffer
			}
		}
	}
	private void deleteMenu(Scanner sc) {
		// Print the user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Delete a Tracker==");
		System.out.format("%15s%-35s%10s%10s%15s","Tracker Number ", "Show", "Episodes",
				"Seasons", "Status");
		for(int i=0; i<trackers.size();i++) {
			Tracker t = trackers.get(i);
			System.out.println();
			System.out.format("%15s%-35s%10d%10d%15s", "[" + (int)(i+1) + "] ", db.getShowById(t.getShowID()).getTitle(), t.getCurrentEpisode(),
					t.getCurrentSeason(), db.getStatus(t.getStatusID()));
			
		} 
		System.out.println();
		System.out.format("%15s%-10s", "[0] ", "Go back");
		System.out.println();
		System.out.println();
		// Prompt user for input
		while(true){
			try {
				System.out.println("Enter the [Tracker Number] to be deleted:");
				
				int userInput=sc.nextInt();
				if (userInput == 0) {
					return;	// Go back to userMenu
				} 
				else if (userInput > 0 && userInput <= trackers.size()) {
					// delete
					db.deleteTracker(trackers.get(userInput-1));
					Show deletedShow =  db.getShowById(trackers.get(userInput-1).getShowID());
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("Tracker for \"" + deletedShow.getTitle() + "\" deleted");
					deleteMenu(sc);
					return;	// Return to the previous menu, either a deleteMenu or the userMenu
					// reprint trackers
//					trackers = db.getAllUserTrackers(sessionID);
//					System.out.println("==Progress Trackers==");
//					System.out.format("%5s%35s%10s%10s%15s","Index", "Show", "Episodes",
//							"Seasons", "Status");
//					for(int i=0; i<trackers.size();i++) {
//						Tracker t = trackers.get(i);
//						System.out.println();
//						System.out.format("%-5s%35s%10d%10d%15s", i+1, db.getShowById(t.getShowID()).getTitle(), t.getCurrentEpisode(),
//								t.getCurrentSeason(), db.getStatus(t.getStatusID()));
//						
//					} System.out.println();
				} 
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid option");
				sc.nextLine();
	        } catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();	// Clear scanner buffer
			}
		}
	}

}
