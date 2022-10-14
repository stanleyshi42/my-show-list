package com.cognixia.jump.myshowlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOClass {
	private Connection conn = ConnManagerWithProperties.getConnection();

	public List<Show> getAllShows() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shows");
			List<Show> showList = new ArrayList<Show>();
			
			while(rs.next()) {
				int id = rs.getInt("showID");
				String title = rs.getString("title");
				int episodes = rs.getInt("episodeCount");
				int seasons = rs.getInt("seasonCount");

				Show show = new Show(id, title, episodes, seasons);
				showList.add(show);
			}
			return showList;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public List<Tracker> getAllTrackers() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM trackers");
			List<Tracker> trackerList = new ArrayList<Tracker>();
			
			while(rs.next()) {
				int userID = rs.getInt("user_id");
				int showID = rs.getInt("show_id");
				int currentEpisode = rs.getInt("current_episode");
				int currentSeason = rs.getInt("current_season");
				int statusID = rs.getInt("s_id");
				
				Tracker trak = new Tracker(userID, showID, currentEpisode, currentSeason, statusID);
				trackerList.add(trak);
			}
			return trackerList;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public Show getShowById(int showID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM shows " +
				"WHERE showID = ?");
			pstmt.setInt(1, showID);

			ResultSet rs = pstmt.executeQuery();
			rs.next();

			String title = rs.getString("title");
			int episodes = rs.getInt("episodeCount");
			int seasons = rs.getInt("seasonCount");

			return new Show(showID, title, episodes, seasons);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public Tracker getTrackerById(int userID, int showID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM trackers " +
				"WHERE showID = ? and userID = ?"
			);
			pstmt.setInt(1, showID);
			pstmt.setInt(2, userID);

			ResultSet rs = pstmt.executeQuery();
			rs.next();

			int episode = rs.getInt("currentEpisode");
			int season = rs.getInt("currentSeason");
			int status = rs.getInt("statusID");

			return new Tracker(userID, showID, episode, season, status);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public List<Tracker> getAllUserTrackers(int userID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM trackers " +
				"WHERE userID = ? " +
				"ORDER BY statusID ASC"
			);
			pstmt.setInt(1, userID);
			ResultSet rs = pstmt.executeQuery();
			
			List<Tracker> trackerList = new ArrayList<Tracker>();
			
			while(rs.next()) {
				int showID = rs.getInt("showID");
				int currentEpisode = rs.getInt("currentEpisode");
				int currentSeason = rs.getInt("currentSeason");
				int statusID = rs.getInt("statusID");

				Tracker tracker = new Tracker(userID, showID, currentEpisode, currentSeason, statusID);
				trackerList.add(tracker);
			}
			return trackerList;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public User getUserByUsername(String username) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM users " +
				"WHERE username = ?"
			);
			pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();
			rs.next();

			int id = rs.getInt("userID");
			String name = rs.getString("username");
			String password = rs.getString("password");

			return new User(id, name, password);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public boolean addTracker(Tracker trak) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"INSERT INTO trackers(userID, showID, currentEpisode, currentSeason, statusID) " +
				"VALUES(?, ?, ?, ?, ?)"
			);
			pstmt.setInt(1, trak.getUserID());
			pstmt.setInt(2, trak.getShowID());
			pstmt.setInt(3, trak.getCurrentEpisode());
			pstmt.setInt(4, trak.getCurrentSeason());
			pstmt.setInt(5, trak.getStatusID());
			
			int i = pstmt.executeUpdate();
			if(i > 0)
				return true;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteTracker(Tracker trak) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"DELETE FROM trackers " +
				"WHERE userID = ? AND showID = ?"
			);
			pstmt.setInt(1, trak.getUserID());
			pstmt.setInt(2, trak.getShowID());
			
			int i = pstmt.executeUpdate();
			if(i > 0)
				return true;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteTrackerByID(int userID, int showID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"DELETE FROM trackers " +
				"WHERE userID = ? AND showID = ?"
			);
			pstmt.setInt(1, userID);
			pstmt.setInt(2, showID);
			
			int i = pstmt.executeUpdate();
			if(i > 0)
				return true;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return false;
	}

	public boolean updateTracker(Tracker trak) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"UPDATE trackers " +
				"SET currentEpisode = ?, currentSeason = ?, statusID = ? " +
				"WHERE userID = ? AND showID = ?"
			);
			pstmt.setInt(1, trak.getCurrentEpisode());
			pstmt.setInt(2, trak.getCurrentSeason());
			pstmt.setInt(3, trak.getStatusID());
			pstmt.setInt(4, trak.getUserID());
			pstmt.setInt(5, trak.getShowID());

			if(pstmt.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return false;
	}

	public String getStatus(int id) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM statuses " +
				"WHERE statusID = ?"
			);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();

			return rs.getString("status");
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public boolean addUser(String username, String password) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
				"INSERT INTO users(username, password) " +
				"VALUES(?, ?)"
			);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			int i = pstmt.executeUpdate();
			if(i > 0)
				return true;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return false;
	}
	
	// Returns the number of users that:
	// statusID = 0; are watching a show
	// statusID = 1; have completed a show
	// statusID = 2; have a show on hold
	// statusID = 3; have dropped a show
	// statusID = 4; plan to watch a show
	public int getShowStatusCount(int showID, int statusID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT COUNT(*) as count FROM trackers " +
					"WHERE showID = ? AND statusID = ?"
			);
			pstmt.setInt(1, showID);
			pstmt.setInt(2, statusID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
