package oauth_2;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.WebAutomation;



public class test1 {

    @Test
    public void auth1() {
    	
    	String[] courseTitles= {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWin8Tuk03gaRlze82zO2xi6RBHp1nBEJU7HPS-6X8d7DwnnFjbFAcGT0Yk6PN856Q&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
        String partialCode=url.split("code=")[1];
		String code = partialCode.split("&scope")[0];	
		System.out.println(code);
		
		String accessTokenresponse = given().urlEncodingEnabled(false)
			.queryParams("code",code)
			.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
			.queryParams("state","verifyfjdss")
			.queryParams("scope","https://www.googleapis.com/auth/userinfo.email")
		.when()
			.log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenresponse);
		String accessToken = js.getString("access_token");
		System.out.println(accessToken);
		
//		String r2= given()
//				.header("Content-Type","application/json")
//		  		.contentType(ContentType.JSON)
//				.queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
//				.when()
//			       .get("https://rahulshettyacademy.com/getCourse.php").asString();
//	    System.out.println(r2);
	    
	    GetCourse gc= given()
				.header("Content-Type","application/json")
		  		.contentType(ContentType.JSON)
				.queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
			       .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
	   
	    System.out.println(gc.getLinkedIn());
	    System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
	    
	    List<API> apiCourses=gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					{
				System.out.println(apiCourses.get(i).getPrice());
					}
		}
		
		//Get the course names of WebAutomation
				ArrayList<String> arr= new ArrayList<String>();
				List<WebAutomation> w=gc.getCourses().getWebAutomation();
				for(int j=0;j<w.size();j++)
				{
					arr.add(w.get(j).getCourseTitle());
				}
			
				List<String> expectedList=	Arrays.asList(courseTitles);
				
				Assert.assertTrue(arr.equals(expectedList));
				
		
	}

   }