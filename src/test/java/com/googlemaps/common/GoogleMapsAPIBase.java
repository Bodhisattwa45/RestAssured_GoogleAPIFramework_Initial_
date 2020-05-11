package com.googlemaps.common;

import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.googlemaps.constants.Auth;
import com.googlemaps.constants.Paths;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

public class GoogleMapsAPIBase {
	
	public static String ENDPOINT;
	public static RequestSpecBuilder REQUEST_BUILDER;
	public static RequestSpecification REQUEST_SPEC;
	public static ResponseSpecBuilder RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPEC;
	public static final String PROPFILEPATH=System.getProperty("user.dir")+"//resource//globalProps.properties";
	public static Properties prop;
	
	public static Properties getProperty()
	{
		prop=new Properties();
		try {
			InputStream inputStream=new FileInputStream(PROPFILEPATH);
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return prop;
	}
	
	public static void setEndpoint(String epoint)
	{
		ENDPOINT=epoint;
	}
	
	public static RequestSpecification getRequestSpecification() {
		
		REQUEST_BUILDER=new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(Paths.BASE_URI);
		REQUEST_BUILDER.setBasePath(Paths.BASE_PATH_MAPS);
		REQUEST_BUILDER.addQueryParam("key", Auth.ACCESS_KEY);
		REQUEST_SPEC=REQUEST_BUILDER.build();
		return REQUEST_SPEC;
	}
	
	public static ResponseSpecification getResponseSpecification()
	{
		RESPONSE_BUILDER=new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(HttpStatus.SC_OK);
		RESPONSE_BUILDER.expectStatusLine("HTTP/1.1 200 OK");
		RESPONSE_BUILDER.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);
		RESPONSE_SPEC=RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}
	
	public static RequestSpecification createQueryParam(RequestSpecification reqSpec,
			String queryParamName, String queryParamValue)
	{
		return reqSpec.queryParam(queryParamName, queryParamValue);
	}
	
	public static RequestSpecification createQueryParam(RequestSpecification reqSpec,
			Map<String,String>queryParamMap)
	{
		return reqSpec.queryParams(queryParamMap);
	}
	
	public static RequestSpecification createPathParam(RequestSpecification reqSpec,
			String pathParamName, String pathParamValue)
	{
		return reqSpec.pathParam(pathParamName, pathParamValue);
	}
	
	public static RequestSpecification createPathParam(RequestSpecification reqSpec,
			Map<String,String> pathParamMap)
	{
		return reqSpec.pathParams(pathParamMap);
	}
	
	public static Response getResponse()
	{
		return given().get(ENDPOINT);
	}
	
	public static Response getResponse(RequestSpecification reqSpec, String methodType)
	{
		REQUEST_SPEC.spec(reqSpec);
		Response response=null;
		switch(methodType)
		{
		  case "get":
			  response=given().spec(REQUEST_SPEC).get(ENDPOINT);
			  break;
			  
		  case "post":
			  response=given().spec(REQUEST_SPEC).post(ENDPOINT);
			  break;
			  
		  case "put":
			  response=given().spec(REQUEST_SPEC).put(ENDPOINT);
			  break;
			  
		  case "delete":
			  response=given().spec(REQUEST_SPEC).delete(ENDPOINT);
			  break;
			  
		  default:
			  String methodName=given().log().method().toString();
			  System.out.println("wrong method type provided:"+methodName);
			  break;
		}
		
		response.then().log().ifError().and().log().ifStatusCodeIsEqualTo(400).and().log().ifValidationFails();
		//response.then().log().all();
		response.then().spec(RESPONSE_SPEC);
		return response;
	}
	
	public static JsonPath getJsonPathObj(Response res)
	{
		String path=res.asString();
		return new JsonPath(path);
	}
	
	public static void setContentType(ContentType type)
	{
		given().contentType(type);
	}

}
