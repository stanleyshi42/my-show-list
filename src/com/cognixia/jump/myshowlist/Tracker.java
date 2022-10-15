package com.cognixia.jump.myshowlist;

public class Tracker {
	private int userId;
	private int showId;
	private int currentEpisode;
	private int currentSeason;
	protected int statusId;
	
	public Tracker(int userId, int showId, int currentEpisode, int currentSeason, int statusId) {
		this.userId = userId;
		this.showId = showId;
		this.currentEpisode = currentEpisode;
		this.currentSeason = currentSeason;
		this.statusId = statusId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	@Override
	public String toString() {
		return "Tracker [userId=" + userId + ", showId=" + showId + ", currentEpisode=" + currentEpisode +
				", currentSeason=" + currentSeason + ", statusId=" + statusId + "]";
	}
}
