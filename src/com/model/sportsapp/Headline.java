package com.model.sportsapp;

import java.util.Date;

public class Headline {
	private String headline;
	private String description;
	private String imageURL;
	private Date published;
	
	public Headline(String headline,String description,String imageURL,Date published){
		this.headline = headline;
		this.description = description;
		this.imageURL = imageURL;
		this.published = published;
		
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}
	
}
