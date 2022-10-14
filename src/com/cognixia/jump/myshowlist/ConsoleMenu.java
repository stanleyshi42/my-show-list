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
		// Get and print a user's trackers
		List<Tracker> trackers = db.getAllUserTrackers(sessionID);
		System.out.println("==Progress Trackers==");
		Helper.printTrackers(trackers, db);
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
				System.out.println("Input was not a valid integer");
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
		
		// Prompt user for input
		while(true) { 
			try {	
				System.out.println();
				System.out.println("Enter a ShowID:" +  " [0] " + " to Go back");		
				int userinput=sc.nextInt();	
				if (userinput == 0)
					return;
				
				// Check if show is already being tracked
				for (Tracker t : trackers) {
					if (userinput == t.getShowID()) {
						System.out.println("Already in the the trackers list");
						addMenu(sc);
						return;
					}					
				} 

				if (userinput > 0 && userinput <= shows.size()) {
					System.out.println("Enter what episode you are on: ");
					int episodeInput=sc.nextInt();	
				if (episodeInput > db.getShowById(userinput).getEpisodes()) {					
						System.out.println("\n");
						System.out.println("The actual max episode is " + db.getShowById(userinput).getEpisodes());
						episodeInput=db.getShowById(userinput).getEpisodes();
					 
					}					
					System.out.println("Enter what season you are on: ");
					int seasonInput=sc.nextInt();
					if (seasonInput >= db.getShowById(userinput).getSeasons()) {							 
						System.out.println("\n");
						System.out.println("The actual max season is " + db.getShowById(userinput).getSeasons());
						seasonInput=db.getShowById(userinput).getSeasons();				
						}
										
					
					System.out.println("==Status options==" + " [0] " + " to Go back");
					System.out.println("[1] Watching");
					System.out.println("[2] Completed");
					System.out.println("[3] On Hold");
					System.out.println("[4] Dropped");	
					System.out.println("[5] Plan to watch ");
					int statusInput=sc.nextInt();
					statusInput--;					
					if (statusInput > 5) {
						do {	System.out.println("\n");
								System.out.println("This is not a valid option");
								System.out.println("==Status options==" + " [0] " + " to Go back");
								System.out.println("[1] Watching");
								System.out.println("[2] Completed");
								System.out.println("[3] On Hold");
								System.out.println("[4] Dropped");	
								System.out.println("[5] Plan to watch ");	
								sc.nextInt();
								
						} while(statusInput > 5);
						
						}	
					else if (statusInput == 0 || episodeInput == 0 || seasonInput == 0) {
							userMenu(sc);
						
						}
					
					
					
					Tracker newTracker = new Tracker(sessionID, userinput, episodeInput, seasonInput, statusInput);
					db.addTracker(newTracker);
					userMenu(sc);
					
				}
			}
			catch(Exception e) {
				e.printStackTrace();

			}
		System.out.println("Show is successfully added");
		}
		
	}
	
	
//	//private void printUpdateMenuItems(List<Show> List<Tracker> Shows) {
//		System.out.format("%10s%35s%10s%10s%15s", "Show Selector", "Show", "ShowID", "Seasons", "Episodes");
//							
//		for(int i=0; i<Shows.size(); i++) {
//		Show s = Shows.get(i);
//			System.out.println();
//		System.out.format("%10s%35s%10s%10s%10s", "[" + (int)(i+1) + "] ",
//				db.getShowById(s.getId()).getTitle(), s.getId(), s.getSeasons(), s.getEpisodes());
//		}
//		System.out.println();
//	}
	// Update Menu
	private void updateMenu(Scanner sc) {
		// TODO Auto-generated method stub
			
			List<Tracker> trackers = db.getAllUserTrackers(sessionID);
			System.out.println("=====Update Menu for All Shows=====");
			Helper.printTrackersWithIndex(trackers, db); 
			System.out.format("%15s%-10s", "[0] ", "Go back");
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
									System.out.println("Already updated in the list");
									updateMenu(sc);
									return;
					}					
				} 
				if (userinput > 0 && userinput <= trackers.size()) {	
					System.out.println("What show are you on now?: ");
					int showInput=sc.nextInt();
					
					System.out.println(" What season you are on: ");
					int seasonInput=sc.nextInt();
					
					System.out.println("What episode are you on?: ");
					int episodeInput=sc.nextInt();
					
					System.out.println("==Status options==");
					System.out.println("[1] Not Completed ");
					System.out.println("[2] In Progress");
					System.out.println("[3] Completed");
					int statusInput=sc.nextInt();
					
//					 if (statusInput > 3) {
//						 do {
//							 System.out.println("\n");
//							 System.out.println("==Status options==");
//								System.out.println("[1] Not Completed ");
//								System.out.println("[2] In Progress");
//								System.out.println("[3] Completed");
//								sc.nextInt();
//							 
//						 }while(statusInput > 3);
//					 }
//					
//				}
					
					Tracker newTracker = new Tracker(sessionID, userinput, episodeInput,
							seasonInput, statusInput);
					db.addTracker(newTracker);
					userMenu(sc);
							return;
				} else {
					throw new MenuOptionException();
				}
							
				
			} catch (InputMismatchException e) {
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
			System.out.println("Show is updated");
		
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

	
