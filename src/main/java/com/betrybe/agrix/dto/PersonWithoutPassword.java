package com.betrybe.agrix.dto;

import com.betrybe.agrix.model.entities.Person;
import com.betrybe.agrix.security.Role;

/**
 * Javadoc.
 */
public record PersonWithoutPassword(Long id, String username, Role role) {

  /**
   * Javadoc.
   */
  public static PersonWithoutPassword convertToDto(Person person) {
    return new PersonWithoutPassword(
        person.getId(),
        person.getUsername(),
        person.getRole()
    );
  }
}