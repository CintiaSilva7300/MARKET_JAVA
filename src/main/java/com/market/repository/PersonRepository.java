package com.market.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.market.models.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
	
	Person findByCode(String code);
	
//	 List<Person> findByNameContainingIgnoreCase(String name);
	
	 @Query("{'$or': [{'name': {$regex: ?0, $options: 'i'}}, {'code': ?1}]}")
	 List<Person> findByNameOrCode(String name, String code);
}
