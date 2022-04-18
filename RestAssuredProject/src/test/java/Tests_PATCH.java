import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class Tests_PATCH {

	@Test
	public void test_1_patch(){
	
	  System.out.println("************");
	  
	  JSONObject jsonObject = new JSONObject();
	  
	  jsonObject.put("name", "nayana");
	  jsonObject.put("job", "doctor");
	  
	  System.out.println(jsonObject);
	  System.out.println(jsonObject.toString());

	  given()
	  		.header("Content-Type","application/json")
	  		.contentType(ContentType.JSON)
	  		.accept(jsonObject.toJSONString())
	  		.body(jsonObject.toJSONString())
	  .when()
	  		.patch("https://reqres.in/api/users/2")
	  .then()
	  		.statusCode(200)
	  		.log().all();
	  
	}
}
