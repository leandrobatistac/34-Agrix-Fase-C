package com.betrybe.agrix.dto;

import com.betrybe.agrix.model.entities.Crop;
import java.time.LocalDate;

/**
 * Javadoc.
 */
public record CropDto(
        Long id,
        String name,
        Double plantedArea,
        LocalDate plantedDate,
        LocalDate harvestDate,
        Long farmId
) {
  /**
   * Javadoc.
   */
  public static CropDto convertToCrop(Crop crop) {
    return new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            crop.getPlantedDate(),
            crop.getHarvestDate(),
            crop.getFarm().getId());
  }
}