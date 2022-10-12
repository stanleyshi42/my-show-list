package com.cognixia.jump.myshowlist;

public class Tracker {
	private int userID;
	private int showID;
	private int currentEpisode;
	private int currentSeason;
	private int statusID;
	
	public Tracker(int userID, int showID, int currentEpisode, int currentSeason, int statusID) {
		this.userID = userID;
		this.showID = showID;
		this.currentEpisode = currentEpisode;
		this.currentSeason = currentSeason;
		this.statusID = statusID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getShowID() {
		return showID;
	}

	public void setShowID(int showID) {
		this.showID = showID;
	}

	public int getCurrentEpisode() {
		return currentEpisode;
	}

	public void setCurrentEpisode(int currentEpisode) {
		this.currentEpisode = currentEpisode;
	}

	public int getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(int currentSeason) {
		this.currentSeason = currentSeason;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	
	@Override
	public String toString() {
		return "Tracker [userID=" + userID + ", showID=" + showID + ", currentEpisode=" + currentEpisode +
				", currentSeason=" + currentSeason + ", statusID=" + statusID + "]";
	}
}
