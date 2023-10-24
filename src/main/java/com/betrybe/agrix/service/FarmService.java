package com.betrybe.agrix.service;

import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.model.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Javadoc.
 */
@Service
public class FarmService {
  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(FarmRepository farmRepository) {
    this.farmRepository = farmRepository;
  }

  public Farm insert(Farm farm) {
    return farmRepository.save(farm);
  }

  public List<Farm> getAll() {
    return farmRepository.findAll();
  }

  public Optional<Farm> getById(Long id) {
    return farmRepository.findById(id);
  }
}