package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.dto.OptimizeTourRequest;
import com.livraison.optimizer.TourOptimizer;

import java.util.List;

public interface TourService {
    List<TourDTO> findAll();
    TourDTO findById(Long id);
    TourDTO save(TourDTO dto);
    TourDTO update(Long id, TourDTO dto);
    void delete(Long id);
    
    // Optimiser un tour existant et retourner le TourDTO mis à jour
    TourDTO optimizeTour(Long id, TourOptimizer optimizer);
    
    // Calculer la distance totale selon l'ordre actuel des livraisons d'un tour
    double getTotalDistance(Long id);
    double getTotalDistanceAfterOptimization(Long id, TourOptimizer optimizer);

    // Créer un tour à partir d'un set de livraisons et l'optimiser
    TourDTO createAndOptimize(OptimizeTourRequest req, TourOptimizer optimizer);
}
