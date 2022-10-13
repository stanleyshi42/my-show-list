package com.cognixia.jump.myshowlist;

import java.util.List;

public interface DAO {
	public List <Show> getAllShows();
	public List <Tracker> getAllTrackers();
    public Show getShowById(int showID);
    public Tracker getTrackerById(int userID, int showID);
    public User getUserByUsername(String username);
    public boolean addUser(User user);
    public boolean addTracker(Tracker trak);
    public boolean deleteTracker(Tracker trak);
    public boolean deleteTrackerByID(int userID, int showID);
    public boolean updateTracker(Tracker trak);
	
}