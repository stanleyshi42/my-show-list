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
					regMenu(sc);
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

	private void regMenu(Scanner sc) {
		sc.nextLine();	// Clear scanner buffer
		while(true){
			try {
				System.out.println("==Register==");
				System.out.println("Enter your desired username, or hit [Enter] to go back:");
				String username=sc.nextLine();
				if(username.isEmpty()) {
					return;	// Go back to welcomeMenu
				}
				System.out.println("Enter your desired password:");
				String password=sc.nextLine();
				if(password.isEmpty()) {
					System.out.println("Invalid password, hit [Enter] to re-enter username");
					regMenu(sc);	// Go back to welcomeMenu
				}
				
				boolean added = db.addUser(username, password);
				// If registration was successful, log the user in
				if(added) {
					sessionID = db.getUserByUsername(username).getUser_id();
					System.out.println("Succesfully registered!");
					userMenu(sc);
				}
				else {
					System.out.println("User is alreadly registered");
					return;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();
			}
		}
	}
	
	private void printTrackers(List<Tracker> trackers) {
		System.out.format("%35s%10s%10s%15s",
						  "Show", "Episodes", "Seasons", "Status");
		for(Tracker t : trackers) {
			System.out.println();
			System.out.format("%35s%10d%10d%15s",
					db.getShowById(t.getShowID()).getTitle(),
					t.getCurrentEpisode(),
					t.getCurrentSeason(),
					db.getStatus(t.getStatusID()));
		}
		System.out.println();
	}
	
	private void printTrackersWithIndex(List<Tracker> trackers) {
		System.out.format("%15s%-35s%10s%10s%15s",
						  "Tracker Number ", "Show", "Episodes", "Seasons", "Status");
		for(int i=0; i<trackers.size();i++) {
			Tracker t = trackers.get(i);
			System.out.println();
			System.out.format("%15s%-35s%10d%10d%15s",
					"[" + (int)(i+1) + "] ",
					db.getShowById(t.getShowID()).getTitle(),
					t.getCurrentEpisode(),
					t.getCurrentSeason(),
					db.getStatus(t.getStatusID()));
		} 
		System.out.println();
	}
	
	private void userMenu(Scanner sc) {
		// Get and print a user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Progress Trackers==");
		printTrackers(trackers);
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
					addMenu(sc);
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
	
	
	private void printAddMenuItems(List<Show> Shows) {
		System.out.format("%10s%35s%10s%10s%15s", "Show Selector", "Show", "ShowID", "Seasons", "Episodes");
		
							
		for(int i=0; i<Shows.size(); i++) {
		Show s = Shows.get(i);
			System.out.println();
		System.out.format("%10s%35s%10s%10s%10s", "[" + (int)(i+1) + "] ",
				db.getShowById(s.getId()).getTitle(), s.getId(), s.getSeasons(), s.getEpisodes());
		}
		System.out.println();
	}
	
	private void addMenu(Scanner sc) {
		// TODO Auto-generated method stub
			List<Show> allshows = db.getAllShows(); 
			List<Tracker> trackers = db.getAllUserTrackers(sessionID);
			System.out.println("=====Add Menu for All Shows=====");
			printAddMenuItems(allshows); 
			System.out.println();
			System.out.println();
			// Prompt user for input
			while(true) { 
			try {	
				System.out.println();
				System.out.println("Enter a ShowID:");		
				int userinput=sc.nextInt();	
				if (userinput == 0) {
					return;
				} 
				for (Tracker t : trackers) {
								if (userinput == t.getShowID()) {
									System.out.println("Already in the the trackers list");
									addMenu(sc);
									return;
					}					
				} 
				if (userinput > 0 && userinput <= allshows.size()) {	
					System.out.println("Enter what episode you are on: ");
					int episodeInput=sc.nextInt();
					
					System.out.println("Enter what season you are on: ");
					int seasonInput=sc.nextInt();
					
					System.out.println("==Status options==");
					System.out.println("[1] Watching");
					System.out.println("[2] Completed");
					System.out.println("[3] On Hold");
					System.out.println("[4] Dropped");	
					System.out.println("[5] Plan to watch ");
					int statusInput=sc.nextInt();
					statusInput--;
					
					Tracker newTracker = new Tracker(sessionID, userinput, episodeInput,
							seasonInput, statusInput);
					db.addTracker(newTracker);
							return;
				}	
							
				
			} catch(Exception e) {
				e.printStackTrace();
			}
						
			

		
		
		
			}
	
		
	}
	
	private void deleteMenu(Scanner sc) {
		// Print the user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Delete a Tracker==");
		printTrackersWithIndex(trackers); 
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
					db.deleteTracker(trackers.get(userInput-1));
					Show deletedShow =  db.getShowById(trackers.get(userInput-1).getShowID());
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("Tracker for \"" + deletedShow.getTitle() + "\" deleted");
					deleteMenu(sc);
					return;	// Return to the previous menu, either a deleteMenu or the userMenu
				} 
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid option");
				sc.nextLine();
	        } 
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();	// Clear scanner buffer
			}
		}
	}
}
