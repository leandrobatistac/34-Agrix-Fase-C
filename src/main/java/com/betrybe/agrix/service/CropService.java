package com.betrybe.agrix.service;

import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.model.repositories.CropRepository;
import com.betrybe.agrix.model.repositories.FertilizerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Javadoc.
 */
@Service
public class CropService {

  private final CropRepository cropRepository;
  private final FertilizerRepository fertilizerRepository;

  @Autowired
  public CropService(CropRepository cropRepository, FertilizerRepository fertilizerRepository) {
    this.cropRepository = cropRepository;
    this.fertilizerRepository = fertilizerRepository;
  }

  public Crop create(Crop crop, Farm farm) {
    crop.setFarm(farm);
    return cropRepository.save(crop);
  }

  public List<Crop> getByFarmId(Long farmId) {
    return cropRepository.findByFarmId(farmId);
  }

  public List<Crop> findAll() {
    return cropRepository.findAll();
  }

  public Optional<Crop> getCropById(Long id) {
    return cropRepository.findById(id);
  }

  /**
   * Javadoc.
   */
  public List<Crop> searchCrops(LocalDate startDate, LocalDate endDate) {
    List<Crop> allCrops = this.cropRepository.findAll();
    List<Crop> searchedCrops = allCrops.stream()
            .filter(crop ->  endDate.isAfter(crop.getHarverstDate())
                    && startDate.isBefore(crop.getHarverstDate()))
            .toList();
    return searchedCrops;
  }

  /**
   * Javadoc.
   */
  public void associateFertilizertoCrop(Long cropId, Long fertilizerId) {
    Optional<Crop> optionalCrop = cropRepository.findById(cropId);
    Optional<Fertilizer> optionalFertilizer = fertilizerRepository.findById(fertilizerId);


    Crop crop = optionalCrop.get();
    crop.getFertilizers().add(optionalFertilizer.get());
    cropRepository.save(crop);
  }

  /**
   * Javadoc.
   */
  public List<Fertilizer> getCropFertilizers(Long cropId) {
    Optional<Crop> optionalCrop = cropRepository.findById(cropId);
    Crop crop = optionalCrop.get();
    return crop.getFertilizers();
  }
}