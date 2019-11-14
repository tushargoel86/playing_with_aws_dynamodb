package com.tushar.dynamodb.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.tushar.dynamodb.model.Hotel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomHotelRepositoryImpl implements CustomHotelRepository {

	@Autowired
	private DynamoDB dynamodb;

	@Autowired
	private DynamoDBMapper mapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
	@Override
	public void createTable() {
		CreateTableRequest request = mapper.generateCreateTableRequest(Hotel.class);
		request.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		
		try {
			Table table = dynamodb.createTable(request);
			table.waitForActive();
			log.info("Successfully created table: " + table.getDescription().getTableStatus());
		} catch(ResourceInUseException | InterruptedException e) {
			log.error("Unable to create table: {} ", e.getMessage());
			throw new DuplicateTableException(e.getMessage());
		}
	}

	@Override
	public void loadData() throws IOException {
	}

	@Override
	public void deleteTable() {
		DeleteTableRequest request = mapper.generateDeleteTableRequest(Hotel.class);
		amazonDynamoDB.deleteTable(request);
		log.info("Table deleted successfully");
	}
	
	@Override
	public void addHotel(Hotel hotel) {
		mapper.save(hotel);
		log.info("New hotel has been added successfully");
	}
	
	@Override
	public Hotel getHotel(String id) {
		Hotel result = mapper.load(new Hotel(id, null));
		return result;
	}
	
	@Override
	public PaginatedScanList<Hotel> fetchAll() {
		 PaginatedScanList<Hotel> hotels = mapper.scan(Hotel.class, new DynamoDBScanExpression());
		 return hotels;
	}



}
