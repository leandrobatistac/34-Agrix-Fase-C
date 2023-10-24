package com.betrybe.agrix.dto;

import com.betrybe.agrix.model.entities.Farm;

/**
 * Javadoc.
 */
public record FarmDto(Long id, String name, Double size) {
  public Farm convertToFarm() {
    return new Farm(id, name, size);
  }
}
