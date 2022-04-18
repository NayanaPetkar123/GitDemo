import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CheckPost {
	
	@Test
	void test_post() throws IOException {
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given()
		.log().all()
		.queryParam("key","qaclick123")
		.header("Content-Type","application/json")
	   // .body(payload.AppPlace())
		.body(new String(Files.readAllBytes(Paths.get("C:\\REST_assured\\new.json"))))
		.when()
				.post("maps/api/place/add/json")
		.then()
				.assertThat().statusCode(200)
				.body("scope",  equalTo("APP"))
				.header("server", "Apache/2.4.18 (Ubuntu)")
				.extract().response().asString();
		
		 System.out.println(response);
		 
		 JsonPath jsonPath = new JsonPath(response); //for parsing Json
		 String placeId = jsonPath.getString("place_id");
		 System.out.println("placeId: " +placeId);
		 
		 //update place
		 String newAdd = "Summer Walk, Africa";
		 
		 given()
			.log().all()
			.queryParam("key","qaclick123")
			.header("Content-Type","application/json")
		    .body("{\r\n" + 
		    		"\"place_id\":\""+placeId+"\",\r\n" + 
		    		"\"address\":\""+newAdd+"\",\r\n" + 
		    		"\"key\":\"qaclick123\"\r\n" + 
		    		"}")
		    .when()
		    		.put("maps/api/place/update/json")
		    .then()
		    		.assertThat().log().all().statusCode(200)
		    		.body("msg", equalTo("Address successfully updated"));
		    
		 //get place
		 
		 String response1=  given()
			.log().all()
			.queryParam("key","qaclick123")
			.queryParam("place_id",placeId)
			.when()
					.get("maps/api/place/get/json")
			.then()
					.assertThat().log().all().statusCode(200)
					.extract().response().asString();
		 
		 JsonPath js = new JsonPath(response1);
		 String actualAddress = js.getString("address");
		 System.out.println("address: " +actualAddress);
		 
		 Assert.assertEquals(actualAddress, newAdd);
		 
	}
	
	/*
	 * public static String GenerateStringFromResource(String path) throws
	 * IOException {
	 * 
	 * return new String(Files.readAllBytes(Paths.get(path)));
	 * 
	 * }
	 */
	
	

}
