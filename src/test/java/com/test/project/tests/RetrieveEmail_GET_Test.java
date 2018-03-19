/**
 * Created by Chandra Chinnaiah
 */
package com.test.project.tests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.test.project.constants.Path;
import com.test.project.utilities.RestUtilities;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import com.test.project.constants.EndPoints;
import static org.hamcrest.Matchers.*;
/**
 * This Test executes REST GET to get email created by URI
 */
public class RetrieveEmail_GET_Test 
{
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	@BeforeClass
	public void setup() {
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.HOTEL);
		resSpec = RestUtilities.getResponseSpecification();
	}
	
	@Test
	public void getEmail() {
		given()
		.spec(RestUtilities.createQueryParam(reqSpec, "/customer", ""))
		.when()
			.get(EndPoints.HOTEL_RETRIEVE_CUSTOMER_EMAIL)
		.then()
			.log().all()
			.spec(resSpec)
			.statusCode(200)
			.body("customerId", containsString("gmail.com"));
	}

}
