import java.util.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class Tests_POST {

	@Test
	public void test_1_post(){
		/*
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * 
		 * map.put("name", "nayana"); map.put("job", "engineer");
		 * 
		 * System.out.println(map);
		 */
	  System.out.println("************");
	  
	  JSONObject jsonObject = new JSONObject();
	  
	  jsonObject.put("name", "nayana");
	  jsonObject.put("job", "engineer");
	  
	  System.out.println(jsonObject);
	  System.out.println(jsonObject.toString());

	  given()
	  		.header("Content-Type","application/json")
	  		.contentType(ContentType.JSON)
	  		.accept(jsonObject.toJSONString())
	  		.body(jsonObject.toJSONString())
	  .when()
	  		.post("https://reqres.in/api/users")
	  .then()
	  		.statusCode(201);
	  
	}
}
