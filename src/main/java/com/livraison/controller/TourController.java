package com.livraison.controller;

import com.livraison.dto.TourDTO;
import com.livraison.optimizer.ClarkeWrightOptimizer;
import com.livraison.optimizer.NearestNeighborOptimizer;
import com.livraison.optimizer.TourOptimizer;
import com.livraison.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        return ResponseEntity.ok(tourService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long id) {
        TourDTO dto = tourService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDTO) {
        TourDTO saved = tourService.save(tourDTO);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Long id, @RequestBody TourDTO dto) {
        TourDTO updated = tourService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/optimize")
    public TourDTO optimizeTour(
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

        return tourService.optimizeTour(id, optimizer);
    }

    @GetMapping("/{id}/distance")
    public double getTotalDistance(@PathVariable Long id) {
        return tourService.getTotalDistance(id);
    }

}
