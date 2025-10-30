package com.livraison.controller;

import com.livraison.dto.TourDTO;
import com.livraison.optimizer.ClarkeWrightOptimizer;
import com.livraison.optimizer.NearestNeighborOptimizer;
import com.livraison.optimizer.TourOptimizer;
import com.livraison.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.livraison.dto.OptimizeTourRequest;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    // 🔹 Récupérer toutes les tournées
    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        return ResponseEntity.ok(tourService.findAll());
    }

    // 🔹 Récupérer une tournée par ID
    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long id) {
        TourDTO dto = tourService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // 🔹 Créer une nouvelle tournée
    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDTO) {
        return ResponseEntity.ok(tourService.save(tourDTO));
    }

    // 🔹 Mettre à jour une tournée
    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Long id, @RequestBody TourDTO dto) {
        TourDTO updated = tourService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 🔹 Supprimer une tournée
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Optimiser une tournée avec un algorithme donné (POST)
    @PostMapping("/{id}/optimize")
    public ResponseEntity<TourDTO> optimizeTour(
            @PathVariable Long id,
            @RequestParam(name = "algorithm", defaultValue = "nearest") String algorithm
    ) {
        TourOptimizer optimizer;

        switch (algorithm.toLowerCase()) {
            case "clarke":
            case "clarkewright":
                optimizer = new ClarkeWrightOptimizer();
                break;
            case "nearest":
            default:
                optimizer = new NearestNeighborOptimizer();
                break;
        }

        return ResponseEntity.ok(tourService.optimizeTour(id, optimizer));
    }

    // 🔹 Même chose mais avec GET (pour test rapide dans navigateur)
    @GetMapping("/{id}/optimize")
    public ResponseEntity<TourDTO> optimizeTourGet(
            @PathVariable Long id,
            @RequestParam(name = "algorithm", defaultValue = "nearest") String algorithm
    ) {
        return optimizeTour(id, algorithm);
    }

    // 🔹 Calculer la distance totale d'une tournée
    @GetMapping("/{id}/distance")
    public ResponseEntity<Double> getTotalDistance(@PathVariable Long id) {
        double distance = tourService.getTotalDistance(id);
        return ResponseEntity.ok(distance);
    }

    // 🔹 Comparer les deux algorithmes (Nearest vs Clarke Wright)
    @GetMapping("/{id}/compare")
    public ResponseEntity<Map<String, Object>> compareAlgorithms(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();

        double distanceNearest = tourService.getTotalDistanceAfterOptimization(id, new NearestNeighborOptimizer());
        double distanceClarke = tourService.getTotalDistanceAfterOptimization(id, new ClarkeWrightOptimizer());

        result.put("tourId", id);
        result.put("nearest_neighbor_distance", distanceNearest);
        result.put("clarke_wright_distance", distanceClarke);
        result.put("better_algorithm", distanceNearest < distanceClarke ? "Nearest Neighbor" : "Clarke Wright");

        return ResponseEntity.ok(result);
    }

    // 🔹 Créer un tour optimisé à partir d'une liste de livraisons
    @PostMapping("/optimize")
    public ResponseEntity<TourDTO> createOptimizedTour(
            @RequestParam(name = "optimizer", defaultValue = "CLARKE_WRIGHT") String optimizer,
            @RequestBody OptimizeTourRequest req
    ) {
        TourOptimizer algo = "NEAREST_NEIGHBOR".equalsIgnoreCase(optimizer)
                ? new NearestNeighborOptimizer()
                : new ClarkeWrightOptimizer();

        TourDTO created = tourService.createAndOptimize(req, algo);
        return ResponseEntity.ok(created);
    }
}
