package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.optimizer.TourOptimizer;

import java.util.List;

public interface TourService {
    List<TourDTO> findAll();
    TourDTO findById(Long id);
    TourDTO save(TourDTO dto);
    TourDTO update(Long id, TourDTO dto);
    void delete(Long id);

    TourDTO optimizeTour(Long tourId, TourOptimizer optimizer);
    double getTotalDistance(Long tourId);
}
