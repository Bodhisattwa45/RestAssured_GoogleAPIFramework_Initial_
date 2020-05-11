package com.petsore.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class PetStorePostOperations {
	@BeforeClass
	public void setupPrerequisites()
	{
		RestAssured.baseURI="https://petstore.swagger.io";
		RestAssured.basePath="/v2";
	}
	
	@Test
	public void createPet()
	{
		given()
		  .header("Content-Type","application/json")
		  .body("{" + 
					"  \"id\": 21221," + 
					"  \"category\": {" + 
					"    \"id\": 1," + 
					"    \"name\": \"dog\"" + 
					"  }," + 
					"  \"name\": \"Arya\"," + 
					"  \"photoUrls\": [" + 
					"    \"insta.pics\"" + 
					"  ]," + 
					"  \"tags\": [" + 
					"    {" + 
					"      \"id\": 54," + 
					"      \"name\": \"ShihTzu\"" + 
					"    }" + 
					"  ]," + 
					"  \"status\": \"available\"" + 
					"}")
	    .when()
	      .post("/pet")
	    .then()
	      .assertThat().statusCode(200);
	}

}
