package com.googlemaps.pojoclasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressComponents {
	
	@JsonProperty private String long_name;
	@JsonProperty private String short_name;
	@JsonProperty private List<String> types;
	
	public String getLong_name() {
		return long_name;
	}
	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}

}
