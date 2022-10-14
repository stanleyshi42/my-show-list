package com.cognixia.jump.myshowlist;

import java.util.List;

public class Helper {
	public static User login(String username, String password, DAOClass db) {
		User user = db.getUserByUsername(username);
		// If login credentials are valid, return the user
		if(user != null && password.equals(user.getPassword())) {
			return user;
		}
		// Else, return null
		return null;
	}
	
	public static void printTrackers(List<Tracker> trackers, DAOClass db) {
		System.out.format("%35s%10s%10s%15s",
						  "Show", "Episodes", "Seasons", "Status");
		System.out.println();
		
		if(trackers.isEmpty()) {
			System.out.format("%35s", "No trackers to display");
		}
		else {
			for(Tracker t : trackers) {
				Show show = db.getShowById(t.getShowID());
				
				System.out.format("%35s%10s%10s%15s",
						show.getTitle(),
						t.getCurrentEpisode() + "/" + show.getEpisodes(),
						t.getCurrentSeason() + "/" + show.getSeasons(),
						db.getStatus(t.getStatusID()));
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public static void printTrackersWithIndex(List<Tracker> trackers, DAOClass db) {
		System.out.format("%9s%-35s%10s%10s%15s",
						  "Tracker ", "Show", "Episodes", "Seasons", "Status");
		System.out.println();
		if(trackers.isEmpty()) {
			System.out.format("%9s%-35s", "", "No trackers to display");
			System.out.println();
		}
		else {
			for(int i=0; i<trackers.size();i++) {
				Tracker t = trackers.get(i);
				Show show = db.getShowById(t.getShowID());
				
				
				System.out.format("%9s%-35s%10s%10s%15s",
						"[" + (int)(i+1) + "] ",
						show.getTitle(),
						t.getCurrentEpisode() + "/" + show.getEpisodes(),
						t.getCurrentSeason() + "/" + show.getSeasons(),
						db.getStatus(t.getStatusID()));
				System.out.println();
			} 
		}
	}
	
	public static void printShowsWithIndex(List<Show> shows) {
		System.out.format("%10s%-35s%10s%10s",
						  "Show ID ", "Show", "Episodes", "Seasons");
							
		for(int i=0; i<shows.size(); i++) {
		Show s = shows.get(i);
		System.out.println();
		System.out.format("%10s%-35s%10s%10s",
						  "[" + s.getId() + "] ",
						  s.getTitle(),
						  s.getEpisodes(),
						  s.getSeasons());
		}
		System.out.println();
	}
}
