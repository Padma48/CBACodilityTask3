package superherosregression;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import excelUtilities.ExcelUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

public class APIRegression {

	private static String APIBaseURL;
	private static String ApiKey;

	@Parameters({ "API_Base_URL", "API_Key" })
	@BeforeTest
	public void beforeTest(String API_Base_URL, String API_Key) {
		APIBaseURL = API_Base_URL;
		ApiKey = API_Key;
		System.out.println("BAse url is" + API_Base_URL);
		Response response = RestAssured.given().header("Authorization", ApiKey).get(APIBaseURL + "/auth/verifytoken")
				.andReturn();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("API Key validation response code is " + response.getStatusCode());
		System.out.println("API Key is valid");

	}

	@Test
	public void users_list() {

		Response response = RestAssured.given().header("Authorization", ApiKey).get(APIBaseURL + "/v1/user")
				.andReturn();
		ResponseBody body = response.body();
		// Converting the response body to string object
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("User list api status code is " + response.getStatusCode());
		System.out.println("User list api for 200 status is successful");

	}

	@Test
	public static void Registration() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("username", "Test");
		requestParams.put("password", "Test@1");
		Response response = RestAssured.given().header("Authorization", ApiKey)
				.header("Content-Type", "application/json").body(requestParams).when()
				.post("https://supervillain.herokuapp.com/auth/user/login").andReturn();
		// response2.body();
		ResponseBody<?> body1 = response.body();
		// Converting the response body to string object
		// String rbdy1 = body.asString();
		// System.out.println("Response is " + rbdy);

		System.out.println("Registration api status code is " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 400);
		System.out.println("Registration api for 400 status successful");
	}

	@Test
	public static void Login() {

		JSONObject requestParams = new JSONObject();
		requestParams.put("username", "Test");
		requestParams.put("password", "Test@1");

		Response response2 = RestAssured.given().header("Authorization", ApiKey)
				.header("Content-Type", "application/json").body(requestParams).when().post("https://supervillain.herokuapp.com/auth/user/login")
				.andReturn();
		// response2.body();
		ResponseBody<?> body = response2.body();
		// Converting the response body to string object
		String rbdy = body.asString();
		// System.out.println("Response is " + rbdy);

		System.out.println("Login api status code is " + response2.getStatusCode());
		Assert.assertEquals(response2.getStatusCode(), 200);
		System.out.println("Login api for 200 status is successful");

	}

	@Test
	public static void AddUser() {

		JSONObject requestParams = new JSONObject();
		requestParams.put("username", "Test");
		requestParams.put("score", "Test@1");

		Response response = RestAssured.given().header("Authorization", ApiKey)
				.header("Content-Type", "application/json").body(requestParams).when().post(APIBaseURL + "/v1/user")
				.andReturn();
		// response2.body();
		ResponseBody<?> body = response.body();
		// Converting the response body to string object
		String rbdy = body.asString();
		// System.out.println("Response is " + rbdy);

		System.out.println("Add user api status code is " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 400);
		System.out.println("Add user api for 400 status successful");

	}

	/*
	 * @Test(dataProvider = "UserDetails") public void
	 * WeatherAPIGetResultByLatAndLon(Object username, Object password) {
	 * 
	 * //Defining the base URI RestAssured.baseURI= APIBase URL;
	 * RequestSpecification httpRequest = RestAssured.given();
	 * 
	 * //Passing the resource details Response res =
	 * httpRequest.queryParams("lat",username, "lon",password,
	 * "key",ApiKey).get("/current"); Response res = httpRequest.body //Retrieving
	 * the response body using getBody() method ResponseBody body = res.body();
	 * //Converting the response body to string object String rbdy =
	 * body.asString(); //Creating object of JsonPath and passing the string
	 * response body as parameter JsonPath jpath = new JsonPath(rbdy); //Storing
	 * publisher name in a string variable String weather =
	 * jpath.getString("data[0].weather.description");
	 * System.out.println("The weather description for latitude " +lat +" is - "
	 * +weather); Assert.assertTrue(weather != null && !weather.trim().isEmpty());
	 * 
	 * }
	 * 
	 * @DataProvider public Object[][] WeatherAPILatAndLonDP() throws IOException {
	 * String path = System.getProperty("user.dir") +
	 * "/src/test/java/WeatherAPIData/WeatherAPIInputData.xlsx"; String
	 * latlonSheetName = "LatAndLon"; int rownum = ExcelUtilities.getRowCount(path,
	 * latlonSheetName); int colnum = ExcelUtilities.getCellCount(path,
	 * latlonSheetName, 1); String latandlondata[][] = new String[rownum][colnum];
	 * for (int i = 1; i <= rownum; i++) { for (int j = 0; j < colnum; j++) {
	 * latandlondata[i - 1][j] = ExcelUtilities.getCellData(path, latlonSheetName,
	 * i, j); } } return latandlondata; }
	 * 
	 * @Parameters({ "API_Base_URL", "API_Key" })
	 * 
	 * @BeforeTest public void beforeTest(String SH_API_Base_URL, String API_Key) {
	 * APIBaseURL = SH_API_Base_URL; ApiKey = API_Key;
	 * System.out.println("Weather_API_Base_URL is " + SH_API_Base_URL);
	 * 
	 * }
	 * 
	 * @AfterClass public void afterClass() { }
	 * 
	 * @Test(dataProvider = "dp") public void f(Integer n, String s) { }
	 * 
	 * @DataProvider public Object[][] dp() { return new Object[][] { new Object[] {
	 * 1, "a" }, new Object[] { 2, "b" }, }; }
	 * 
	 * 
	 * @AfterTest public void afterTest() { }
	 */

}
