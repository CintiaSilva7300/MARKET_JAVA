package com.market.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.models.Person;
import com.market.repository.PersonRepository;
import com.market.utils.GenericCode;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PersonController {
	
	private final PersonRepository personRepository;
	
	public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
	
	@PostMapping("/person")
	public ResponseEntity<String> createAccount(@Valid @RequestBody Person person){
		String personCode = GenericCode.generateCode("PERSON-");
		
		person.setCode(personCode);
		person.setRegisterDate(new Date());
		
		personRepository.save(person);
		
		return new ResponseEntity<>("Pessoa registrada com sucesso! ", HttpStatus.CREATED);
	}
	
	@GetMapping("/person")
    public List<Person> getAllPeople(@RequestParam(name = "personCodePrefix", defaultValue = "PERSON-") String personCodePrefix) {
        // Retorna tudo salvo no MongoDB
        return personRepository.findAll();
    }
}
