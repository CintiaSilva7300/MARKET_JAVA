package com.market.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	 //atualiza pelo personCode
	 @PutMapping("/person/{personCode}")
	 public ResponseEntity<String> updateAccount(@PathVariable String personCode, @RequestBody Person updatedAccount) {
	     // Encontra o account pelo código no MongoDB
	     Person existingAccount = personRepository.findByCode(personCode);

	     if (existingAccount != null) {
	         try {
	        	 existingAccount.setFirstName(updatedAccount.getFirstName());
	        	 existingAccount.setSecondName(updatedAccount.getSecondName());
	        	 existingAccount.setEmail(updatedAccount.getEmail());
	        	 existingAccount.setPhone(updatedAccount.getPhone());
	        	 existingAccount.setPassword(updatedAccount.getPassword());
	        	 existingAccount.setConfirmPassword(updatedAccount.getConfirmPassword());

	             // Atualiza no MongoDB
	             personRepository.save(existingAccount);

	             return new ResponseEntity<>("Conta foi atualizado com sucesso", HttpStatus.OK);
	         } catch (Exception e) {
	             return new ResponseEntity<>("Erro ao atualizar o conta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	         }
	     } else {
	         return new ResponseEntity<>("Conta não encontrado", HttpStatus.NOT_FOUND);
	     }
	 }
	 
	 @GetMapping("/person/filter/{personCode}")
	 public ResponseEntity<List<Person>> getAccountByFilter(@PathVariable String personCode) {
	     // Verifica se é um código válido
	     if (personCode != null && !personCode.isEmpty()) {
	         Person foundPerson = personRepository.findByCode(personCode);

	         if (foundPerson != null) {
	             List<Person> filteredAccount = Collections.singletonList(foundPerson);
	             return new ResponseEntity<>(filteredAccount, HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	         }
	     } else {
	         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	 }
}
