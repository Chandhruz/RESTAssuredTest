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
 * This Test executes REST Delete to delete email created by URI
 */
public class DeleteEmail_DELETE_Test 
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
	public void deleteEmail() {
		given()
		.spec(RestUtilities.createQueryParam(reqSpec, "/customer", ""))
		.when()
			.delete(EndPoints.HOTEL_DELETE_CUSTOMER_EMAIL)
		.then()
			.log().all()
			.spec(resSpec)
			.statusCode(200)
			.body("deleted", equalTo(true));
	
	}

}
