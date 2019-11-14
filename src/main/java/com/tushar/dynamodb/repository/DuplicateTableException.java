package com.tushar.dynamodb.repository;

public class DuplicateTableException extends RuntimeException {
	
	public DuplicateTableException(String message) {
		super(message);
	}

}
