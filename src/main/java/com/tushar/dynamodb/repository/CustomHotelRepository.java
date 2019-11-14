package com.tushar.dynamodb.repository;

import java.io.IOException;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tushar.dynamodb.model.Hotel;

public interface CustomHotelRepository {
	
	void createTable();
	void deleteTable();
	void loadData() throws IOException;
	void addHotel(Hotel hotel);
	Hotel getHotel(String id);
	PaginatedScanList<Hotel> fetchAll();

}
