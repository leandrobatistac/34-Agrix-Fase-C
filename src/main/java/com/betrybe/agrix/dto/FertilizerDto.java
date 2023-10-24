package com.betrybe.agrix.dto;

import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.model.entities.Fertilizer;

/**
 * Javadoc.
 */
public record FertilizerDto(
        Long id,
        String name,
        String brand,
        String composition
) {

  /**
   * Javadoc.
   */
  public static FertilizerDto convertToFertilizerDto(Fertilizer fertilizer) {
    return new FertilizerDto(
            fertilizer.getId(),
            fertilizer.getName(),
            fertilizer.getBrand(),
            fertilizer.getComposition()
    );
  }

  public Fertilizer convertToFertilizer() {
    return new Fertilizer(id, name, brand, composition);
  }

}