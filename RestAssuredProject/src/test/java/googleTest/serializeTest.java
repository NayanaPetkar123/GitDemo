package googleTest;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

public class serializeTest {
	
	
	@Test
	public void test(){
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		pojo p= new pojo();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName("Frontline house");
	
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shoe");
		
		p.setTypes(myList);
		
		location l = new location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		Response res = given()
				.log().all()
				.queryParam("key","qaclick123")
				.header("Content-Type","application/json")
			   // .body(payload.AppPlace())
				.body(p)
				.when()
						.post("maps/api/place/add/json")
				.then()
						.assertThat().statusCode(200)
						//.body("scope",  equalTo("APP"))
						//.header("server", "Apache/2.4.18 (Ubuntu)")
						.extract().response();
		
		System.out.println(res.asString());
		
	}


}
