package com.cognixia.jump.myshowlist;

import java.util.List;
	
	// DAO interface
	// Here we list the CRUD methods we may need to make changes and
	// retrieve info from our database
	public interface DAO {

		public List <Show> getAllShows();
		public List <Tracker> getAllTrackers();
	    public Show getShowById(int deptId);
	    public Tracker getTrackerById(int user_id, int show_id);
	    public boolean validateUser(String username, String password);
	    public boolean addTracker(Tracker trak);
	    public boolean deleteTracker(Tracker trak);
	    public boolean updateTracker(Tracker trak);
		
	}