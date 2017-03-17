package com.smita.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	@JsonProperty
	private String display_name;

	public String getDisplayName() {
		return display_name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getDisplayName();
	}
	
	
}
