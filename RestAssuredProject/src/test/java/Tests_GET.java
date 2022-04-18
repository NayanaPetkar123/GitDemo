import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Tests_GET {
	
	
	@Test
	public void test_1_get() {
		
		given()
		.header("Content-Type","application/json")
		.when()
		.get("https://reqres.in/api/users?page=2")
		.then()
		.statusCode(200)
		.body("data.id[1]", equalTo(8))
		.body("data.first_name[0]", equalTo("Michael"))
		.body("data.first_name", 
				hasItems("Michael","Lindsay","Tobias","Byron","George"))
		.log().all();
		
	}

}
