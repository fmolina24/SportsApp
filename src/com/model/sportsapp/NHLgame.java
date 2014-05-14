package com.model.sportsapp;

import java.util.Date;


public class NHLgame {
	private int gameid;
	private String home_team;
	private String away_team;
	private Integer home_goals;
	private Integer away_goals;
	private String status;
	private Date time;
	private String homeLogo;
	private String awayLogo;
	private String tv;
	
	
	public NHLgame(int gameid,String home_team,String away_team,Integer home_gaols,
			Integer away_goals,String status,Date time,String homeLogo,
			String awayLogo,String tv){
		this.gameid = gameid;
		this.home_team = home_team;
		this.away_team = away_team;
		this.home_goals = home_goals;
		this.away_goals = away_goals;
		this.status = status;
		this.time = time;
		this.homeLogo = homeLogo;
		this.awayLogo = awayLogo;
		this.tv = tv;
		
		
	}
	

	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public String getHomeLogo() {
		return homeLogo;
	}


	public void setHomeLogo(String homeLogo) {
		this.homeLogo = homeLogo;
	}


	public String getAwayLogo() {
		return awayLogo;
	}


	public void setAwayLogo(String awayLogo) {
		this.awayLogo = awayLogo;
	}


	public String getTv() {
		return tv;
	}


	public void setTv(String tv) {
		this.tv = tv;
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

	public Integer getHome_goals() {
		return home_goals;
	}

	public void setHome_goals(int home_goals) {
		this.home_goals = home_goals;
	}

	public Integer getAway_goals() {
		return away_goals;
	}

	public void setAway_goals(int away_goals) {
		this.away_goals = away_goals;
	}
	

}
