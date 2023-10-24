package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.CropDto;
import com.betrybe.agrix.dto.FarmDto;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FarmService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {
  private final FarmService farmService;
  private final CropService cropService;

  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  @PostMapping()
  public ResponseEntity<Farm> insertFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.insert(farmDto.convertToFarm());
    return ResponseEntity.status(HttpStatus.CREATED).body(newFarm);
  }

  @GetMapping()
  public ResponseEntity<Object> getAll() {
    List<Farm> allFarms = farmService.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(allFarms);
  }

  /**
   * Javadoc.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    Optional<Farm> farmById = farmService.getById(id);

    if (farmById.isPresent()) {
      return ResponseEntity.ok().body(farmById);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
  }

  /**
   * Javadoc.
   */
  @PostMapping("/{farmId}/crops")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createCrop(@PathVariable Long farmId, @RequestBody Crop crop) {
    Optional<Farm> farmEspecifica = farmService.getById(farmId);

    if (farmEspecifica.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    } else {
      Crop createdCrop = cropService.create(crop, farmEspecifica.get());
      return ResponseEntity.status(HttpStatus.CREATED).body(CropDto.convertToCrop(createdCrop));
    }
  }

  /**
   * Javadoc.
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<?> getCropsById(@PathVariable Long farmId) {
    Optional<Farm> farm = farmService.getById(farmId);

    if (farm.isPresent()) {
      return ResponseEntity.ok(cropService.getByFarmId(farmId).stream()
              .map(crop -> new CropDto(
                      crop.getId(),
                      crop.getName(),
                      crop.getPlantedArea(),
                      crop.getPlantedDate(),
                      crop.getHarvestDate(),
                      crop.getFarm().getId())));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }
  }
}
