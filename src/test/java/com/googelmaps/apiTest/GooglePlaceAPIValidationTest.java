package com.googelmaps.apiTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.googlemaps.common.GoogleMapsAPIBase;
import com.googlemaps.constants.Endpoints;
import com.googlemaps.pojoclasses.AddressComponents;
import com.googlemaps.pojoclasses.GeometryLatLong;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GooglePlaceAPIValidationTest {
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	private Map<String,String> queryParamsMap=null;
	
	@BeforeClass
	public void testSetup()
	{
		reqSpec=GoogleMapsAPIBase.getRequestSpecification();
		resSpec=GoogleMapsAPIBase.getResponseSpecification();
		queryParamsMap=new HashMap<String,String>();
		queryParamsMap.put("place_id", GoogleMapsAPIBase.getProperty().getProperty("placeId"));
		queryParamsMap.put("fields", "address_component" +","+"adr_address"+","+"formatted_address"+","+
				"geometry"+","+"icon"+","+"name"+","+"permanently_closed"+","+"place_id"+","+"photo"+","+
				"plus_code"+","+"type"+","+"url"+","+"utc_offset"+","+"vicinity"+","+"formatted_phone_number"+","+
				"international_phone_number"+","+"opening_hours"+","+"website"+","+
				"price_level"+","+"rating"+","+"review"+","+"user_ratings_total");
	}
	
	@Test
	public void getResponsePlaceAPI()
	{
		GoogleMapsAPIBase.setEndpoint(Endpoints.MAPS_PLACES);
		Response resPlaceApi=GoogleMapsAPIBase.getResponse(
				GoogleMapsAPIBase.createQueryParam(reqSpec, queryParamsMap), "get");
		
		/*given().queryParam("place_id", GoogleMapsAPIBase.getProperty().getProperty("placeId"))
		 .and().queryParam("fields", "address_component" +","+"adr_address"+","+"formatted_address"+","+
					"geometry"+","+"icon"+","+"name"+","+"permanently_closed"+","+"place_id"+","+"photo"+","+
					"plus_code"+","+"type"+","+"url"+","+"utc_offset"+","+"vicinity"+","+"formatted_phone_number"+","+
					"international_phone_number"+","+"opening_hours"+","+"website"+","+
					"price_level"+","+"rating"+","+"review"+","+"user_ratings_total")
		.when()
		 .get("/place/details/json")
		.then().statusCode(HttpStatus.SC_OK)
		 .and().statusLine("HTTP/1.1 200 OK")
		 .log().ifError().and().log().ifStatusCodeIsEqualTo(400).and().log().ifValidationFails();
		 
		 .assertThat().rootPath("result")
		 .body("place_id", equalTo(prop.getProperty("placeId")),
				 "name",equalTo("Central Park"),
				 "address_components.short_name",hasItem("Manhattan"))
		 .extract().response();*/
				
		JsonPath placeResJson=GoogleMapsAPIBase.getJsonPathObj(resPlaceApi);
		placeResJson.setRootPath("result");
		
		String placeIdToVerify=placeResJson.getString("place_id");
		assertThat(placeIdToVerify, equalTo(GoogleMapsAPIBase.getProperty().getProperty("placeId")));
		
		String nameToVerify=placeResJson.getString("name");
		assertThat(nameToVerify, equalTo("Central Park"));
		
		List<String> short_names=new ArrayList<String>();
		short_names=placeResJson.getList("address_components.short_name");
		assertThat(short_names, hasItem("Manhattan"));
		
		List<String> shortAddComponents=resPlaceApi.path("result.address_components.short_name","Address Components");
		assertThat("address component in response", shortAddComponents.size(),equalTo(5));
		
		/*String placeIdResponse=resPlaceApi.body().asString();
		JsonPath placeResJsonObj=new JsonPath(placeIdResponse);*/
		
		GeometryLatLong location=placeResJson.getObject("geometry.location", GeometryLatLong.class);
		assertThat(location.getLatitude(), equalTo(40.782864));
		assertThat(location.getLongitude(), equalTo(-73.965355));
		
		List<AddressComponents> addressComponent=placeResJson.getList("address_components", AddressComponents.class);
		List<String> typesRes;
		for(int i=0;i<addressComponent.size();i++)
		{
			typesRes=addressComponent.get(i).getTypes();
			assertThat(typesRes, hasItem("political"));
		}
	}

}
