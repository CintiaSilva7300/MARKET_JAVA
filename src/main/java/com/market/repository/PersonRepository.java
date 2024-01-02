package com.market.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.market.models.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
	
	Person findBycode(String code);
}
