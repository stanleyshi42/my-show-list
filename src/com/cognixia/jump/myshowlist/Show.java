package com.cognixia.jump.myshowlist;

public class Show {
	private int id;
	private String title;
	private int episodes;
	private int seasons;

	public Show(int id, String title, int episodes, int seasons) {
		super();
		this.id = id;
		this.title = title;
		this.episodes = episodes;
		this.seasons = seasons;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}
	
	public int getSeasons() {
		return seasons;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	@Override
	public String toString() {
		return "Show [id=" + id + ", title=" + title + ", episodes=" + episodes + ", seasons=" + seasons + "]";
	}
}