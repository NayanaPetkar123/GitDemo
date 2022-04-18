package googleTest;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {
	
	
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
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
							.addQueryParam("key","qaclick123")
							.setContentType(ContentType.JSON).build();
	
		
		ResponseSpecification respo = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();
		
		RequestSpecification res = given().spec(req)
				.body(p);
			
		Response response = res.when()
						.post("maps/api/place/add/json")
				.then()
						.spec(respo)
						//.assertThat().statusCode(200)
						//.body("scope",  equalTo("APP"))
						//.header("server", "Apache/2.4.18 (Ubuntu)")
						.extract().response();
		
		System.out.println(response.asString());
		
	}


}
