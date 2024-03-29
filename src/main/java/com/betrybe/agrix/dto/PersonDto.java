package com.betrybe.agrix.dto;

import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.security.Role;

/**
 * Javadoc.
 */
public record PersonDto(Long id, String username, String password, Role role) {
  /**
   * Javadoc.
   */
  public Person toEntity() {
    Person person = new Person();
    person.setUsername(this.username);
    person.setPassword(this.password);
    person.setRole(this.role);
    return person;
  }

  /**
   * Javadoc.
   */
  public static PersonDto convertToDto(Person person) {
    return new PersonDto(
        person.getId(),
        person.getUsername(),
        person.getPassword(),
        person.getRole()
    );
  }
}