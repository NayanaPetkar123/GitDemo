package dynamic;

public class payload {

	public static String Addbook(String isbn, String aisle) {
		String payload = "{\r\n" + 
				"\"name\":\"Core Java with Andrew\",\r\n" + 
				"\"isbn\":\""+isbn+"\",\r\n" + 
				"\"aisle\":\""+aisle+"\",\r\n" + 
				"\"author\":\"Andrew Johnson\"\r\n" + 
				"}\r\n" + 
				"";
		
		return payload;
	}
}
