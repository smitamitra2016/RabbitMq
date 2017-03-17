package com.smita.helpers;

public class LatitudeGenerator {

	private static String latitide = "35.5487429714954";
	
	public static String getLatitide(){
		float latitude = Float.parseFloat(latitide);
		latitude+=1;
		LatitudeGenerator.latitide=String.valueOf(latitude);
		return LatitudeGenerator.latitide;
	}
	
}
