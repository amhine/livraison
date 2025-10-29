package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;

import java.util.List;

public class ClarkeWrightOptimizer implements TourOptimizer {

    @Override
    public List<Delivery> calculateOptimalTour(Warehouses warehouse, List<Delivery> deliveries) {
        return deliveries;
    }

    @Override
    public double calculateTotalDistance(Warehouses warehouse, List<Delivery> deliveries) {
        return 0;
    }
}
