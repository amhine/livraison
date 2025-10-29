package com.livraison.optimizer;

import com.livraison.entity.Delivery;
import com.livraison.entity.Warehouses;

import java.util.List;

public interface TourOptimizer {
    List<Delivery> calculateOptimalTour(Warehouses warehouse, List<Delivery> deliveries);
    double calculateTotalDistance(Warehouses warehouse, List<Delivery> orderedDeliveries);
}
