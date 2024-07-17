package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setupData() 
	{
		
		faker=new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//log
		logger=LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void testPostUser() 
	{
		logger.info("*********************** Creating user **************************************");
		Response response =  UserEndPoints.creatUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*********************** user is created Successfully **************************************");
	}
	
	@Test(priority=2)
	public void testGetUser() 
	{
		Response response =  UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3)
	public void testUpdateUser() 
	{
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response =  UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		//checking data after updattion
		Response responseAgfterUpdate =  UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUser() 
	{
		Response response =  UserEndPoints.deleteUser(this.userPayload.getUsername());
//		response.then().log().all();		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	
}
