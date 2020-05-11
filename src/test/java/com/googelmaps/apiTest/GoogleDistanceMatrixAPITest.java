package com.googelmaps.apiTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.googlemaps.common.GoogleMapsAPIBase;
import com.googlemaps.constants.Endpoints;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GoogleDistanceMatrixAPITest {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	@BeforeClass
	public void setPrerequisite() {
		reqSpec=GoogleMapsAPIBase.getRequestSpecification();
		
		Map<String,String> queryParamsMap=new HashMap<String,String>();
		queryParamsMap.put("units", "imperial");
		queryParamsMap.put("origins", "Las+Vegas");
		queryParamsMap.put("destinations", "New+York+City,NY");
		reqSpec.spec(GoogleMapsAPIBase.createQueryParam(reqSpec, queryParamsMap));
		
		resSpec=GoogleMapsAPIBase.getResponseSpecification();
	}
	
	@Test
	public void validateResponseBody()
	{
	  Response getistanceMatrixResponse=
	   given()
		.spec(reqSpec)
	   .when()
	    .get(Endpoints.MAPS_DISTANCEMATRIX)
	   .then()
	    .spec(resSpec)
	    //.log().all()
	    .body("rows[0].elements[0].distance.text", equalTo("2,522 mi"))
	   .extract().response();
	  
	  JsonPath resObj=GoogleMapsAPIBase.getJsonPathObj(getistanceMatrixResponse);
	  String status=resObj.getString("status");
	  assertThat(resObj,is(notNullValue()));
	  assertThat(status, equalTo("OK"));
	}

}
