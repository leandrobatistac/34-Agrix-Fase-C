package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.PersonDto;
import com.betrybe.agrix.dto.PersonWithoutPassword;
import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc.
 */
@RestController
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Javadoc.
   */
  @PostMapping("/persons")
  public ResponseEntity<PersonWithoutPassword> createPerson(@RequestBody PersonDto personDto) {
    Person person = personService.create(personDto.toEntity());
    PersonWithoutPassword data = PersonWithoutPassword.convertToDto(person);
    return ResponseEntity.status(HttpStatus.CREATED).body(data);
  }
}