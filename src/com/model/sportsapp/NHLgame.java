package com.model.sportsapp;


public class NHLgame {
	private int gameid;
	private String home_team;
	private String away_team;
	private int home_goals;
	private int away_goals;
	private String status;
	
	public NHLgame(int gameid,String home_team,String away_team,int home_gaols,int away_goals,String status){
		this.gameid = gameid;
		this.home_team = home_team;
		this.away_team = away_team;
		this.home_goals = home_goals;
		this.away_goals = away_goals;
		this.status = status;
		
	}
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public String getHome_team() {
		return home_team;
	}

	public void setHome_team(String home_team) {
		this.home_team = home_team;
	}

	public String getAway_team() {
		return away_team;
	}

	public void setAway_team(String away_team) {
		this.away_team = away_team;
	}

	public int getHome_goals() {
		return home_goals;
	}

	public void setHome_goals(int home_goals) {
		this.home_goals = home_goals;
	}

	public int getAway_goals() {
		return away_goals;
	}

	public void setAway_goals(int away_goals) {
		this.away_goals = away_goals;
	}
	

}
