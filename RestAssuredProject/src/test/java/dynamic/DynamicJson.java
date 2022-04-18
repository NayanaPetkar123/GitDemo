package dynamic;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	//content of the file to String-> content of file can convert into byte->byte data to String

	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		
		// addbook
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given()
		.header("Content-Type","application/json")
		.body(payload.Addbook(isbn, aisle))
		.when()
			.post("/Library/Addbook.php")
		.then()
			.assertThat().statusCode(200)
			.extract().response().asString();		
		
		 JsonPath js1 = new JsonPath(response);
		 String id=js1.get("ID");
		 System.out.println("Id: "+id);
		 
		//deletebook
		 
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		return new Object[][] { {"shsusg","589jh"},
			{"jeub2i","657hd"},
			{"tyene","753jf"}};
	}
	
	
}
