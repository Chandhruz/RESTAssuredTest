/**
 * Created by Chandra Chinnaiah
 */

package com.test.project.tests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.test.project.constants.Path;
import com.test.project.utilities.ReadDataFromExcel;
import com.test.project.utilities.RestUtilities;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import com.test.project.constants.EndPoints;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**This Test Iterates over InputTestData.xlsx for test input values as below to validate JSON Response Values for POST Request
 * 1. Valid Data
 * 2. Wrong Password
 * 3. Missing Title
 * 4. Missing LastName
 * 5. Numeric Values for FirstName and LastName
 * 6. Special Characters for LastName
 * 7. Maximum Length Exceeded for LastName
 */
public class CreateEmail_POST_Test 
{
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	public static ReadDataFromExcel excel = null;
	
	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.HOTEL);
		resSpec = RestUtilities.getResponseSpecification();
	}
	
	@Test(dataProvider = "getData")
	public void createEmailValidAndInvalidInputFieldValues(Hashtable<String, String> data) {
		
		Map<String,String> accCreate = new HashMap<>();
		accCreate.put("email", data.get("Email"));
		accCreate.put("password", data.get("Password"));
		accCreate.put("firstName", data.get("FirstName"));
		accCreate.put("lastName", data.get("LastName"));
		accCreate.put("title", data.get("Title"));

		
		given()
		.spec(RestUtilities.createQueryParam(reqSpec, "", ""))
		.contentType("application/json")
		.body(accCreate)
		.when()
			.post(EndPoints.HOTEL_CREATE_CUSTOMER_EMAIL)
		.then()
			.log().all()
			.spec(resSpec)
			.statusCode(Integer.parseInt(data.get("StatusCode")))
			.body("body.customerId",equalTo(data.get("ExpectedEmail")))
			.body("body.success",equalTo(true));	

	}
	
	 @DataProvider
	  public static Object[][] getData() {
	    if (excel == null) {
	      excel = new ReadDataFromExcel("resources\\InputTestData.xlsx");
	    }

	    String sheetName = "Data";
	    int rows = excel.getRowCount(sheetName);
	    int columns = excel.getColumnCount(sheetName);
	    Object[][] data = new Object[rows - 1][1];
	    Hashtable<String, String> table = null;
	    for (int rowNums = 2; rowNums <= rows; rowNums++) {
	      table = new Hashtable<String, String>();
	      for (int colNum = 0; colNum < columns; colNum++) {

	        table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNums));
	        data[rowNums - 2][0] = table;
	      }
	    }

	    return data;
	  }
}