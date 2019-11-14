package com.tushar.dynamodb.dynamodndemo;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.tushar.dynamodb.model.Hotel;
import com.tushar.dynamodb.repository.HotelRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@TestPropertySource(properties = { 
  "awsAccessKey=<accessKey>", 
  "awsSecretkey=<secret key>" })
class DynamodnDemoApplicationTests {

	private DynamoDBMapper dynamoDBMapper;
	  
    @Autowired
    private AmazonDynamoDB amazonDynamoDB;
    
    @Autowired
	private DynamoDB dynamodb;
  
    @Autowired
    HotelRepository repository;
  
    
    @BeforeEach
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Hotel.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        Table table = dynamodb.createTable(tableRequest);
		table.waitForActive();
    }
    
    @Test
    public void dynamoDBTestCase() {
        Hotel hotel = new Hotel(null, "leela");
        repository.addHotel(hotel);
        PaginatedScanList<Hotel> hotels = repository.fetchAll();
        assertTrue(hotels.size() > 0);
        assertTrue("The hotel name is correct.", hotels.get(0).getName().equals("leela"));
    }
    
    @AfterEach
    public void delete() {
    	DeleteTableRequest request = dynamoDBMapper.generateDeleteTableRequest(Hotel.class);
		amazonDynamoDB.deleteTable(request);
    }

}
