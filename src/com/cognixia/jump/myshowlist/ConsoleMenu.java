package com.cognixia.jump.myshowlist;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.cognixia.jump.exceptions.LoginException;
import com.cognixia.jump.exceptions.MenuOptionException;

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
					throw new MenuOptionException();
				}
			} 
			catch (InputMismatchException e) {
				System.out.println("Input was not a valid integer");
				sc.nextLine();
			}
			catch (MenuOptionException e) {
				System.out.println(e.getMessage());
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
				
				User user = Helper.login(username, password, db);
				// If login was successful, log the user in
				if(user != null) {
					sessionID = user.getUser_id();
					userMenu(sc);
					return;
				}
				else {
					throw new LoginException();
				}
			}
			catch(LoginException e) {
				System.out.println(e.getMessage());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
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
					throw new LoginException("password");
				}

				// If registration was successful, log the user in
				if(db.addUser(username, password)) {
					Helper.login(username, password, db);
					System.out.println("Succesfully registered!");
					userMenu(sc);
					return;
				}
				else {
					throw new LoginException(1);
				}
			}
			catch(LoginException e) {
				System.out.println(e.getMessage());
			}
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();
			}
		}
	}
	
	private void userMenu(Scanner sc) {
		while(true){
			try {
				// Get and print a user's trackers
				List<Tracker> trackers = db.getAllUserTrackers(sessionID);
				System.out.println("==Progress Trackers==");
				Helper.printTrackers(trackers, db);
				System.out.println();
				System.out.println("Enter an option:");
				System.out.println("[1] Update a tracker");
				System.out.println("[2] Add a tracker");
				System.out.println("[3] Delete a tracker");
				System.out.println("[4] Logout");
				
				int userInput=sc.nextInt();
				switch (userInput) {
				case 1:
					updateMenu(sc);
					break;
				case 2:
					addMenu(sc);
					break;
				case 3:
					deleteMenu(sc);
					break;
				case 4:
					sessionID = -1;	// Logout the current user
					return;	// Go back to loginMenu
				default:
					throw new MenuOptionException();
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid menu option");
				sc.nextLine();
			} catch (MenuOptionException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
	        } catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();	// Clear scanner buffer
			}
		}
	}
	
	private void addMenu(Scanner sc) {
		List<Show> shows = db.getAllShows(); 
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("=====Add a Tracker=====");
		Helper.printShowsWithIndex(shows);
		System.out.println("      [0] " + "Go back");
		
		// Prompt user for input
		while(true) { 
			try {
				// Prompt user for show to track
				int showInput;
				do {
					System.out.println("Enter a [ShowID] to track:");	
					showInput = sc.nextInt();
					if(showInput < 0 || showInput > shows.size())
						System.out.println("Invalid ShowID");
					// Check if show is already being tracked
					for (Tracker t: trackers) {
						if (showInput == t.getShowID()) {
							System.out.println("Already tracking "+db.getShowById(t.getShowID()).getTitle());
							showInput = -1;
						}
					}
				} while(showInput < 0 || showInput > shows.size());

				// Prompt user for episodes watched
				int episodeInput;
				do {
					System.out.println("Enter episodes watched: ");
					episodeInput=sc.nextInt();
					
					// Check for negative input
					if(episodeInput < 0)
						System.out.println("Invalid episode input");
					// If episode input is greater than episode count, set it to the latest episode
					else if (episodeInput > db.getShowById(showInput).getEpisodes())				
						episodeInput = db.getShowById(showInput).getEpisodes();
				} while(episodeInput < 0);

				// Prompt user for episodes watched
				int seasonInput;
				do {
					System.out.println("Enter seasons watched: ");
					seasonInput=sc.nextInt();
					
					// Check for negative input
					if(seasonInput < 0)
						System.out.println("Invalid seasons input");
					// If season input is greater than season count, set it to the latest season
					else if (seasonInput > db.getShowById(showInput).getEpisodes())				
						seasonInput = db.getShowById(showInput).getEpisodes();
				} while(seasonInput < 0);
				
				// Prompt user for show status
				int statusInput;
				do {
					System.out.println("==Show Status Options==");
					System.out.println("Enter a [Show Status] option:");
					System.out.println("[1] Watching");
					System.out.println("[2] Completed");
					System.out.println("[3] On Hold");
					System.out.println("[4] Dropped");	
					System.out.println("[5] Plan to watch ");
					statusInput=sc.nextInt();
					if(statusInput < 1 || statusInput > 5)
						System.out.println("Invalid option");
				} while(statusInput < 1 || statusInput > 5);
				
				Tracker newTracker = new Tracker(sessionID, showInput, episodeInput, seasonInput, --statusInput);
				db.addTracker(newTracker);
				if (db.addTracker(newTracker)) 
					System.out.println("Now tracking " + db.getShowById(newTracker.getShowID()).getTitle());
				
				return; // Return to previous menu
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Update Menu
	private void updateMenu(Scanner sc) {
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("=====Update a Tracker=====");
		Helper.printTrackersWithIndex(trackers, db);
		System.out.println("      [0] " + "Go back");
		
		while(true) { 
			try {
				// Prompt user for a tracker to update
				int trackerInput;
				do {
					System.out.println("Enter a [Tracker Number] to update:");	
					trackerInput = sc.nextInt();
					if(trackerInput < 0 || trackerInput > trackers.size())
						System.out.println("Invalid tracker number");
				} while(trackerInput < 0 || trackerInput > trackers.size());

				// Prompt user for episodes watched
				int episodeInput;
				do {
					System.out.println("Enter episodes watched, or [Enter] to skip: "); //TODO implement skip
					episodeInput=sc.nextInt();
					
					// Check for negative input
					if(episodeInput < 0)
						System.out.println("Invalid episode input");
					// If episode input is greater than episode count, set it to the latest episode
					else if (episodeInput > db.getShowById(trackerInput).getEpisodes())				
						episodeInput = db.getShowById(trackerInput).getEpisodes();
				} while(episodeInput < 0);

				// Prompt user for seasons watched
				int seasonInput;
				do {
					System.out.println("Enter seasons watched, or [Enter] to skip: : ");
					seasonInput=sc.nextInt();
					
					// Check for negative input
					if(seasonInput < 0)
						System.out.println("Invalid seasons input");
					// If season input is greater than season count, set it to the latest season
					else if (seasonInput > db.getShowById(trackerInput).getEpisodes())				
						seasonInput = db.getShowById(trackerInput).getEpisodes();
				} while(seasonInput < 0);
				
				// Prompt user for show status
				int statusInput;
				do {
					System.out.println("==Show Status Options==");
					System.out.println("Enter a [Show Status] option:");
					System.out.println("[1] Watching");
					System.out.println("[2] Completed");
					System.out.println("[3] On Hold");
					System.out.println("[4] Dropped");	
					System.out.println("[5] Plan to watch ");
					statusInput=sc.nextInt();
					if(statusInput < 1 || statusInput > 5)
						System.out.println("Invalid option");
				} while(statusInput < 1 || statusInput > 5);
				
				Tracker newTracker = new Tracker(sessionID, trackerInput, episodeInput, seasonInput, --statusInput);
				db.addTracker(newTracker);
				if (db.updateTracker(newTracker)) 
					System.out.println("Updated your tracker for " + db.getShowById(newTracker.getShowID()).getTitle());
				
				return; // Return to previous menu
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void deleteMenu(Scanner sc) {
		// Print the user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Delete a Tracker==");
		Helper.printTrackersWithIndex(trackers, db); 
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
				} else {
					throw new MenuOptionException();
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Input was not a valid integer");
				sc.nextLine();
			}
			catch (MenuOptionException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
	        } 
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();	// Clear scanner buffer
			}
		}
	}
}
