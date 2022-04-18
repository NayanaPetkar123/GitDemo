package realtime_JIRA;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class Jira_CommentGetIsuue {
	
	@Test
	public void jiratest() {
		
		RestAssured.baseURI= "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		
		String response= given().header("Content-Type","application/json")
		.body("{ \r\n" + 
				"	\"username\": \"Nayana\", \r\n" + 
				"	\"password\": \"nayana1996@N\"\r\n" + 
				"	\r\n" + 
				"}").log().all().filter(session)
		.when()
			.post("/rest/auth/1/session")
		.then()
			.extract().response().asString();
		
		String expectedMsg = "Hi I am verifying the message";
		
		//Add comment
		
		String addCommentResponse = given().pathParam("key", "10103").log().all().header("Content-Type","application/json")
		.body("{\r\n" + 
				"    \"body\": \""+expectedMsg+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session)
		.when()
			.post("/rest/api/2/issue/{key}/comment")
		.then()
			.assertThat().statusCode(201).extract().response().asString();
		
		JsonPath jsonpath = new JsonPath(addCommentResponse);
		String commentId = jsonpath.getString("id");
		
		
		
		//Add attachment
		
		given().header("X-Atlassian-Token", "no-check").filter(session)
		.pathParam("key", "10103")
		.header("Content-Type","multipart/form-data")
		.multiPart("file", new File("jira.txt"))
		.when()
			.post("/rest/api/2/issue/{key}/attachments")
		.then()
			.log().all().assertThat().statusCode(200);
		
		
		//Get issue
		
		String getIssue= given().filter(session).pathParam("key", "10103")
		.queryParam("fields", "comment")
		.when()
			.get("rest/api/2/issue/{key}")
		.then()
			.log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(getIssue);
		int commentsCount = js.getInt("fields.comment.comments.size()");
		for(int i =0;i<commentsCount;i++) {
			String commentIdIssue = js.get("fields.comment.comments["+i+"].id").toString();
			
			if (commentIdIssue.equalsIgnoreCase(commentId)) {
				String msg = js.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(msg);
				
				Assert.assertEquals(msg, expectedMsg);
			
				
				
			}
		}
		
	}

}
