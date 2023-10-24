package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.CropDto;
import com.betrybe.agrix.dto.FertilizerDto;
import com.betrybe.agrix.except.NotFoundMsg;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FertilizerService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc.
 */
@RestController
@RequestMapping("/crops")
public class CropsController {

  public final CropService cropService;
  public final FertilizerService fertilizerService;

  @Autowired
  public CropsController(CropService cropsService, FertilizerService fertilizerService) {
    this.cropService = cropsService;
    this.fertilizerService = fertilizerService;
  }


  /**
   * Javadoc.
   */
  @GetMapping()
  public ResponseEntity<?> findAll() {
    List<Crop> allCrops = cropService.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(allCrops.stream()
            .map(crop -> new CropDto(
                    crop.getId(),
                    crop.getName(),
                    crop.getPlantedArea(),
                    crop.getPlantedDate(),
                    crop.getHarvestDate(),
                    crop.getFarm().getId())));
  }

  /**
   * Javadoc.
   */
  @GetMapping("/search")
  public ResponseEntity<?> searchCrop(@RequestParam LocalDate start, @RequestParam LocalDate end) {
    List<Crop> allCrops = this.cropService.searchCrops(start, end);
    return ResponseEntity.status(HttpStatus.OK).body(allCrops.stream()
            .map(crop -> new CropDto(
                    crop.getId(),
                    crop.getName(),
                    crop.getPlantedArea(),
                    crop.getPlantedDate(),
                    crop.getHarvestDate(),
                    crop.getFarm().getId()
            )));
  }

  /**
   * Javadoc.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getCropById(@PathVariable Long id) {
    Optional<Crop> cropById = cropService.getCropById(id);
    if (cropById.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(CropDto.convertToCrop(cropById.get()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }
  }

  /**
   * Javadoc.
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> associateFertilizerToCrop(
      @PathVariable Long cropId, @PathVariable Long fertilizerId) {
    Optional<Crop> cropById = cropService.getCropById(cropId);
    Optional<Fertilizer> fertilizerById = fertilizerService.findById(fertilizerId);

    if (cropById.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }

    if (fertilizerById.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fertilizante não encontrado!");
    }

    cropService.associateFertilizertoCrop(cropId, fertilizerId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Fertilizante e plantação associados com sucesso!");
  }

  /**
   * Javadoc.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<?> getCropFertilizers(@PathVariable Long cropId) {
    Optional<Crop> cropById = cropService.getCropById(cropId);

    if (cropById.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(cropById.get().getFertilizers());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }

  }
}
