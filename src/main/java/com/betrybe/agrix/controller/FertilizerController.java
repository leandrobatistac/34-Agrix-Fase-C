package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.FertilizerDto;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.service.FertilizerService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Javadoc.
 */
@RestController
@RequestMapping("/fertilizers")
public class FertilizerController {

  public final FertilizerService fertilizerService;

  @Autowired
  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Javadoc.
   */
  @PostMapping()
  public ResponseEntity<Fertilizer> insertFertilizer(@RequestBody FertilizerDto fertilizerDto) {
    Fertilizer newFertilizer = fertilizerService.create(fertilizerDto.convertToFertilizer());
    return ResponseEntity.status(HttpStatus.CREATED).body(newFertilizer);
  }

  /**
   * Javadoc.
   */
  @GetMapping()
  public ResponseEntity<?> findAll() {
    List<Fertilizer> allFertilizer = fertilizerService.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(allFertilizer.stream()
        .map(fertilizer -> new FertilizerDto(
            fertilizer.getId(),
            fertilizer.getName(),
            fertilizer.getBrand(),
            fertilizer.getComposition())));
  }

  /**
   * Javadoc.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getFertilizerById(@PathVariable Long id) {
    Optional<Fertilizer> fertilizerById = fertilizerService.findById(id);
    if (fertilizerById.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(FertilizerDto.convertToFertilizerDto(fertilizerById.get()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fertilizante não encontrado!");
    }
  }
}
