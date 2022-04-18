import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class NestedJson {

	@Test
	
	public void test() {
	
	JsonPath js = new JsonPath(payload.CoursePrice());
	
	//Print no of courses returned by API
	
	int count = js.getInt("courses.size()");
	System.out.println("No of courses: "+count);
	
	System.out.println("***************");
	
	//Print Purchase Amount
	
	int amount = js.getInt("dashboard.purchaseAmount");
	System.out.println("Amount: "+amount);
	
	System.out.println("***************");
	
	//Print title of the first course
	
	String title =js.getString("courses.title[0]");
	System.out.println("title of first course: "+title);
	
	System.out.println("***************");
	
	//Print all course titles and their respective prices
	
	for(int i=0;i<count;i++) {
		String courseTitle= js.get("courses.title["+i+"]");
		Integer price= js.get("courses.price["+i+"]");
		System.out.println("course title: "+courseTitle+ " and price: "+price);
	}
	
	System.out.println("***************");
	
	//Print no of copies sold by RPA Courses

	for(int i=0;i<count;i++)
	{
		String courseTitle= js.get("courses.title["+i+"]");
		if(courseTitle.equalsIgnoreCase("RPA")) {
			Integer copies= js.get("courses.copies["+i+"]");
			System.out.println("Print no of copies sold by RPA Courses: " +copies);
			break;
		}
	}
	
	System.out.println("***************");
	
	// verify the sum
	
	int sum= 0;
	
	for(int i=0;i<count;i++) {
	  int price= js.getInt("courses.price["+i+"]");
	  int copies= js.getInt("courses.copies["+i+"]");
	  int totalAmount = price * copies;
	  System.out.println(totalAmount);
	  sum = sum + totalAmount;
	}
	
	 System.out.println("sum is: "+sum);
	
	 JsonPath js1 = new JsonPath(payload.CoursePrice());
	 int actualAmount = js1.get("dashboard.purchaseAmount");
	 System.out.println("purchaseAmount is : " +actualAmount);
	 

	 
	}
}
