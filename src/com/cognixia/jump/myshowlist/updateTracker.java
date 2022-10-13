package com.cognixia.jump.myshowlist;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class updateTracker extends Tracker implements Serializable {
	
public updateTracker(int userID, int showID, int currentEpisode, int currentSeason, int statusID) {
		super(userID, showID, currentEpisode, currentSeason, statusID);
		// TODO Auto-generated constructor stub
	}

//	private int userID;
//	private int showId;
//	private int currentEpisode;
//	private int currentSeason;
//	private int statusID;
//	private boolean progress = false;
//	
//	public void int()
//	{
//		
//	}
//	public void progress(InProgress p)
//	{
//		progress = p.get();
//		progress = true;
//		
//	}
//	public 
	
	protected void runUpdate() {
		try {
			Object statusID = null;
			Object currentTitle = statusID;
			Object currentEpisode = statusID;
			Object currentSeason = statusID;
			
			
			
			updateProgress progress = updateProgress.START;
			
			
			
			
		} catch (Exception e) {
			
			switch(statusID) {
			case 1: 
	//		currentTitle;
			System.out.println("Update current Title");
			break;
			
			case 2: 
	//		currentEpisode;
			System.out.println("Update current Episode");
			break;
			
			case 3:
	//		currentSeason;
			System.out.println("Update current Season");
			break;
			
			default: 
				System.out.println();
			
			}
			
		}
	}
	
}


