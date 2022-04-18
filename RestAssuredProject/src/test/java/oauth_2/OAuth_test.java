package oauth_2;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuth_test {

	@Test
	public void auth() throws InterruptedException {
		
		
		 System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe"); 
	     WebDriver driver = new ChromeDriver();
	     driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&flowName=GeneralOAuthFlow");
	     driver.findElement(By.cssSelector("input[type='email']")).sendKeys("nayanapetkar143");
	     driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
	     
	     Thread.sleep(3000);
	     
	     driver.findElement(By.cssSelector("input[type='password']")).sendKeys("nayana1196");
	     driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
	     Thread.sleep(3000);
	     String url= driver.getCurrentUrl();
		
		//String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWgRwJUpkZXol6_W68EK1Z3F5SNjMN5tZ5ACcxvHxOQtc4lJatuzB0FCSoa4chJUQQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		
		String partialCode=url.split("code=")[1];
		String code = partialCode.split("&scope")[0];	
		System.out.println(code);
		
		String accessTokenresponse = given().urlEncodingEnabled(false)
			.queryParams("code",code)
			.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
			.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
			.queryParams("grant_type","authorization_code")
			//.queryParams("scope","https://www.googleapis.com/auth/userinfo.email")
		.when()
			.log().all()
			.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenresponse);
		String accesstoken = js.getString("access_token");
		System.out.println(accesstoken);
		
		
		String response = given()
			.queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON)
		.when()
			.get("https://rahulshettyacademy.com/getCourse.php")
		.asString();
		
		System.out.println(response);
		
		
		
	}
}
