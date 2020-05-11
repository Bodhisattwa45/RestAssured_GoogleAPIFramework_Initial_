package com.googlemaps.pojoclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeometryLatLong {
	
	@JsonProperty private double lat;
	@JsonProperty private double lng;
	
	public double getLatitude() {
        return lat;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }
    
	public double getLongitude() {
        return lng;
    }

    public void setLongitude(double lng) {
        this.lng = lng;
    }

}
