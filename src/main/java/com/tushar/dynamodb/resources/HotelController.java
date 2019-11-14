package com.tushar.dynamodb.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.tushar.dynamodb.model.Hotel;
import com.tushar.dynamodb.repository.DuplicateTableException;
import com.tushar.dynamodb.repository.HotelRepository;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelRepository hotelRepository;
	
	@GetMapping("/create")
	public ResponseEntity<String> createTable() {
		hotelRepository.createTable();
		return new ResponseEntity<String>("table created", HttpStatus.CREATED);
	}
	
	@DeleteMapping()
	public ResponseEntity<String> deleteTable() {
		hotelRepository.deleteTable();
		return new ResponseEntity<String>("table deleted", HttpStatus.ACCEPTED);
	}
	
	
	//@ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicate table name")
	@ExceptionHandler(value = { DuplicateTableException.class })
	public ResponseEntity<String> duplicateTable(DuplicateTableException e) {
	  	return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<String> resourceNotFound(ResourceNotFoundException e) {
	  	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	@PostMapping
	public ResponseEntity<String> addTable(@RequestBody Hotel hotel) {
		hotelRepository.addHotel(hotel);
		return new ResponseEntity<String>("New hotel has been added", HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<String> getHotel(@PathVariable String id) {
		Hotel hotel = hotelRepository.getHotel(id);
		return new ResponseEntity<String>("Hotel is: " + hotel.getName(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping
	public PaginatedScanList<Hotel> allHotels() {
		return hotelRepository.fetchAll();
	}
	
	
}
