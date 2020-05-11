package com.petsore.api;

import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class VerifyCreatedPetResource {
	
	@BeforeClass
	public void setupPrerequisites()
	{
		RestAssured.baseURI="https://petstore.swagger.io";
		RestAssured.basePath="/v2";
	}
	
	@Test
	public void validateStatusCodeAndNameGetPetById()
	{
		given().pathParam("petId", "21221")
		.header("Content-Type", "application/json")
	    .when()
	      .get("/pet/{petId}")
	    .then()
	      .assertThat()
	      .statusCode(200).and()
	      .body("name", equalTo("Arya"));
	}
	
	@AfterTest
	public void getResponseBody()
	{
		Response resPetById = 
		given().pathParam("petId", "21221")
		  .header("Content-Type", "application/json")
	    .when()
	      .get("/pet/{petId}")
	    .then()
	      .extract().response();
		
		System.out.println(resPetById.body().asString());
		String responseByPetId=resPetById.asString();
		
		JsonPath jsonRes=new JsonPath(responseByPetId);
		String name=jsonRes.get("name");
		System.out.println(name);
		
		int tagsSize=jsonRes.getInt("tags.size()");
		String[] tagNames=new String[tagsSize];
		for(int i=0;i<tagsSize;i++)
		{
			tagNames[i]=jsonRes.getString("tags["+i+"].name");
			System.out.println(tagNames[i]);
		}
	}

}
