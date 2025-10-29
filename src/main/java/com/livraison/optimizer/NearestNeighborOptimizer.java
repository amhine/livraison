package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import com.livraison.util.DistanceUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NearestNeighborOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> calculateOptimalTour(Warehouses warehouse, List<Delivery> deliveries) {
        List<Delivery> remaining = new ArrayList<>(deliveries);
        List<Delivery> ordered = new ArrayList<>();

        double[] current = { warehouse.getLatitude(), warehouse.getLongitude() }; // [lat, lon]

        while (!remaining.isEmpty()) {
            Delivery nearest = remaining.stream()
                    .min(Comparator.comparingDouble(d -> DistanceUtils.calculateDistance(
                            current[0], current[1], d.getLatitude(), d.getLongitude())))
                    .get();

            ordered.add(nearest);
            remaining.remove(nearest);

            current[0] = nearest.getLatitude();
            current[1] = nearest.getLongitude();
        }

        return ordered;
    }


    @Override
    public double calculateTotalDistance(Warehouses warehouse, List<Delivery> deliveries) {
        double total = 0;
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        for (Delivery d : deliveries) {
            total += DistanceUtils.calculateDistance(currentLat, currentLon, d.getLatitude(), d.getLongitude());
            currentLat = d.getLatitude();
            currentLon = d.getLongitude();
        }

        // retour au dépôt
        total += DistanceUtils.calculateDistance(currentLat, currentLon, warehouse.getLatitude(), warehouse.getLongitude());
        return total;
    }
}
