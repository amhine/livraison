package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;
import com.livraison.util.DistanceCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClarkeWrightOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> optimize(Warehouses warehouse, List<Delivery> deliveries) {
        // Étape 1 : Calcul des économies (savings)
        record Saving(Delivery i, Delivery j, double value) {}

        List<Saving> savings = new ArrayList<>();

        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                double d0i = DistanceCalculator.calculateDistance(
                        warehouse.getLatitude(), warehouse.getLongitude(),
                        deliveries.get(i).getLatitude(), deliveries.get(i).getLongitude());

                double d0j = DistanceCalculator.calculateDistance(
                        warehouse.getLatitude(), warehouse.getLongitude(),
                        deliveries.get(j).getLatitude(), deliveries.get(j).getLongitude());

                double dij = DistanceCalculator.calculateDistance(
                        deliveries.get(i).getLatitude(), deliveries.get(i).getLongitude(),
                        deliveries.get(j).getLatitude(), deliveries.get(j).getLongitude());

                double saving = d0i + d0j - dij;
                savings.add(new Saving(deliveries.get(i), deliveries.get(j), saving));
            }
        }

        // Étape 2 : Trier les économies décroissantes
        savings.sort(Comparator.comparingDouble(Saving::value).reversed());

        // Étape 3 : Construction du tour simplifié
        List<Delivery> result = new ArrayList<>();
        for (Saving s : savings) {
            if (!result.contains(s.i())) result.add(s.i());
            if (!result.contains(s.j())) result.add(s.j());
        }

        return result;
    }
}
