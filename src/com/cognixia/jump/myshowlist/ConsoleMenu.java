package com.cognixia.jump.myshowlist;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.cognixia.jump.exceptions.LoginException;
import com.cognixia.jump.exceptions.MenuOptionException;

public class ConsoleMenu {
	private DAOClass db = new DAOClass();
	private int sessionId = -1;	// Tracks which user is currently logged in
	
	public void welcomeMenu() {
		Scanner sc = new Scanner(System.in);
		
		while(true){
			try {
				System.out.println("==Welcome to myShowList!==");
				System.out.println("Enter an option:");
				System.out.println("[1] Login");
				System.out.println("[2] Register");
				System.out.println("[3] View show stats");
				System.out.println("[4] Exit program");
				
				int userInput = sc.nextInt();
				switch (userInput) {
				case 1:
					loginMenu(sc);
					break;
				case 2:
					regMenu(sc);
					break;
				case 3:
					statsMenu(sc);
					break;
				case 4:
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
					sessionId = user.getId();
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
				sc.nextLine();
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
					User user = Helper.login(username, password, db);
					sessionId = user.getId();
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
				List<Tracker> trackers = db.getAllUserTrackers(sessionId);
				System.out.println("==Progress Trackers==");
				Helper.printTrackers(trackers, db);
				System.out.println();
				System.out.println("Enter an option:");
				System.out.println("[1] Update a tracker");
				System.out.println("[2] Add a tracker");
				System.out.println("[3] Delete a tracker");
				System.out.println("[4] View show stats");
				System.out.println("[5] Logout");
				
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
					statsMenu(sc);
					break;
				case 5:
					sessionId = -1;	// Logout the current user
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
	
	private void updateMenu(Scanner sc) {
		List<Tracker> trackers = db.getAllUserTrackers(sessionId);
		System.out.println("=====Update a Tracker=====");
		Helper.printTrackersWithIndex(trackers, db);
		System.out.format("%9s%-35s",
				  		  "[0] ", "Go back");
		System.out.println("\n");
		
		while(true) {
			try {
				// Prompt user for a tracker to update
				boolean validInput = false;
				int trackerInput=-1;
				do {
					System.out.println("Enter a [Tracker] to update:");	
					trackerInput = sc.nextInt();
					if(trackerInput < 0 || trackerInput > trackers.size()) {
						System.out.println("Invalid tracker number");
					}
					else if(trackerInput == 0)
						return;
					else
						validInput = true;
				} while(!validInput);
				
				// Get show from the selected tracker
				Show show = db.getShowById(trackers.get(--trackerInput).getShowId());
				Tracker tracker = db.getTrackerById(sessionId, show.getId());
				
				// Prompt user for episodes watched
				validInput = false;
				int episodeInput;
				do {
					System.out.println("Enter episodes watched, or [-1] to make no update:");
					episodeInput=sc.nextInt();
					
					// If user skips, don't change their tracker information
					if(episodeInput == -1) {
						episodeInput = tracker.getCurrentEpisode();
						validInput = true;
					}
					// Check for negative input
					else if(episodeInput < 0)
						System.out.println("Invalid episode input");
					else
						validInput = true;
					
					// If episode input is greater than episode count, set it to the latest episode
					if (episodeInput > show.getEpisodes())				
						episodeInput = show.getEpisodes();
				} while(!validInput);

				// Prompt user for seasons watched
				validInput = false;
				int seasonInput;
				do {
					System.out.println("Enter seasons watched, or [-1] to make no update:");
					seasonInput=sc.nextInt();
					
					if(seasonInput == -1) {
						seasonInput = tracker.getCurrentSeason();
						validInput = true;
					}
					else if(seasonInput < 0)
						System.out.println("Invalid season input");
					else
						validInput = true;
					
					// If season input is greater than season count, set it to the latest season
					if (seasonInput > show.getSeasons())				
						seasonInput = show.getSeasons();
				} while(!validInput);
				
				// Prompt user for show status
				validInput = false;
				int statusInput;
				do {
					System.out.println("==Show Status Options==");
					System.out.println("Enter a [Show Status], or [-1] to make no update:");
					System.out.println("[1] Watching");
					System.out.println("[2] Completed");
					System.out.println("[3] On Hold");
					System.out.println("[4] Dropped");	
					System.out.println("[5] Plan to watch");
					statusInput=sc.nextInt();

					if(statusInput == -1) {
						statusInput = tracker.getStatusId();
						validInput = true;
					}
					else if(statusInput < 1 || statusInput > 5) {
						System.out.println("Invalid option");
					}
					else {
						validInput = true;
						statusInput--;	// Change input to the status's actual ID
					}
						
				} while(!validInput);
				
				Tracker newTracker = new Tracker(sessionId, show.getId(), episodeInput, seasonInput, statusInput);
				if (db.updateTracker(newTracker)) {
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("Updated tracker for " + show.getTitle());
				}
				else {
					System.out.println("Failed to update");
					System.out.println();
				}
				
				return; // Return to previous menu
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();
			}
		}
	}
	
	private void addMenu(Scanner sc) {
		List<Show> shows = db.getAllShows(); 
		List<Tracker> trackers = db.getAllUserTrackers(sessionId);
		System.out.println("=====Add a Tracker=====");
		Helper.printShowsWithIndex(shows);
		System.out.format("%10s%-35s",
						  "[0] ", "Go back");
		System.out.println("\n");
		
		// Prompt user for input
		while(true) {
			try {
				// Prompt user for show to track
				boolean validInput = false;
				int showInput;
				do {
					System.out.println("Enter a [ShowID] to track:");	
					showInput = sc.nextInt();
					// Check if show is already being tracked
					for (Tracker t: trackers) {
						if (showInput == t.getShowId()) {
							System.out.println("Already tracking "+db.getShowById(t.getShowId()).getTitle());
							showInput = -1;
						}
					}
					if(showInput < 0 || showInput > shows.size())
						System.out.println("Invalid ShowID");
					else if(showInput == 0)
						return;
					else
						validInput = true;
				} while(!validInput);
				
				// Get the selected show
				Show show = db.getShowById(showInput);

				// Prompt user for episodes watched
				validInput = false;
				int episodeInput;
				do {
					System.out.println("Enter episodes watched: ");
					episodeInput=sc.nextInt();
					
					// Check for negative input
					if(episodeInput < 0)
						System.out.println("Invalid episode input");
					else
						validInput=true;
					// If episode input is greater than episode count, set it to the latest episode
					if (episodeInput > show.getEpisodes())				
						episodeInput = show.getEpisodes();
				} while(!validInput);

				// Prompt user for episodes watched
				validInput = false;
				int seasonInput;
				do {
					System.out.println("Enter seasons watched: ");
					seasonInput=sc.nextInt();
					
					// Check for negative input
					if(seasonInput < 0)
						System.out.println("Invalid season input");
					else
						validInput = true;
					// If season input is greater than season count, set it to the latest season
					if (seasonInput > show.getSeasons()) {			
						seasonInput = show.getSeasons();
					}	
				} while(!validInput);
				
				// Prompt user for show status
				validInput = false;
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
					else
						validInput = true;
				} while(!validInput);
				
				Tracker newTracker = new Tracker(sessionId, showInput, episodeInput, seasonInput, --statusInput);
				if (db.addTracker(newTracker)) {
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("Now tracking " + show.getTitle());
				}
				else
					System.out.println("Failed to add tracker");
				
				addMenu(sc);
				return; // Return to previous menu
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
			catch(Exception e) {
				e.printStackTrace();
				sc.nextLine();
			}
		}
	}
	
	private void deleteMenu(Scanner sc) {
		// Print the user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionId);
		System.out.println("==Delete a Tracker==");
		Helper.printTrackersWithIndex(trackers, db); 
		System.out.format("%9s%-10s", "[0] ", "Go back");
		System.out.println("\n");
		
		// Prompt user for input
		while(true){
			try {
				System.out.println("Enter a [Tracker] to delete:");

				int userInput=sc.nextInt();
				if (userInput == 0) {
					return;	// Go back to userMenu
				} 
				else if (userInput > 0 && userInput <= trackers.size()) {
					db.deleteTracker(trackers.get(userInput-1));
					Show deletedShow =  db.getShowById(trackers.get(userInput-1).getShowId());
					System.out.println("-------------------------------------------------------------------------------------");
					System.out.println("Deleted tracker for " + deletedShow.getTitle());
					deleteMenu(sc);
					return;	// Return to the previous menu
				} else {
					throw new MenuOptionException();
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input");
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
	
	private void statsMenu(Scanner sc) {
		try {
			List<Show> shows = db.getAllShows();
			System.out.println("==Show Stats==");
			Helper.printShowStats(shows, db);
			System.out.println();
			System.out.println("Press [Enter] to go back");
			sc.nextLine();
			sc.nextLine();
			return;
			
		} catch (InputMismatchException e) {
			System.out.println("Invalid menu option");
			sc.nextLine();
		}  catch(Exception e) {
			e.printStackTrace();
			sc.nextLine();	// Clear scanner buffer
		}
		
	}
}
