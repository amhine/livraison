package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.entity.*;
import com.livraison.mapper.TourMapper;
import com.livraison.optimizer.TourOptimizer;
import com.livraison.repository.*;
import com.livraison.util.DistanceCalculator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final TourMapper tourMapper;

    public TourServiceImpl(TourRepository tourRepository,
                           VehicleRepository vehicleRepository,
                           WarehouseRepository warehouseRepository,
                           DeliveryRepository deliveryRepository,
                           TourMapper tourMapper) {
        this.tourRepository = tourRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.deliveryRepository = deliveryRepository;
        this.tourMapper = tourMapper;
    }

    @Override
    public List<TourDTO> findAll() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TourDTO findById(Long id) {
        return tourRepository.findById(id)
                .map(tourMapper::toDTO)
                .orElse(null);
    }

    @Override
    public TourDTO save(TourDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElse(null);
        Warehouses warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElse(null);
        List<Delivery> deliveries = dto.getDeliveryIds() != null ?
                deliveryRepository.findAllById(dto.getDeliveryIds()) : null;

        Tour tour = tourMapper.toEntity(dto, vehicle, warehouse, deliveries);
        return tourMapper.toDTO(tourRepository.save(tour));
    }

    @Override
    public TourDTO update(Long id, TourDTO dto) {
        Optional<Tour> existing = tourRepository.findById(id);
        if (existing.isEmpty()) return null;

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElse(null);
        Warehouses warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElse(null);
        List<Delivery> deliveries = dto.getDeliveryIds() != null ?
                deliveryRepository.findAllById(dto.getDeliveryIds()) : null;

        Tour updatedTour = tourMapper.toEntity(dto, vehicle, warehouse, deliveries);
        updatedTour.setId(id);
        return tourMapper.toDTO(tourRepository.save(updatedTour));
    }

    @Override
    public void delete(Long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public TourDTO optimizeTour(Long tourId, TourOptimizer optimizer) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour non trouvé"));

        List<Delivery> optimizedDeliveries = optimizer.optimize(
                tour.getWarehouses(), tour.getDeliveries()
        );

        tour.setDeliveries(optimizedDeliveries);

        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toDTO(savedTour);
    }


    @Override
    public double getTotalDistance(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour non trouvé"));

        List<Delivery> deliveries = tour.getDeliveries();
        double distance = 0.0;
        double currentLat = tour.getWarehouses().getLatitude();
        double currentLon = tour.getWarehouses().getLongitude();

        for (Delivery d : deliveries) {
            distance += DistanceCalculator.calculateDistance(currentLat, currentLon, d.getLatitude(), d.getLongitude());
            currentLat = d.getLatitude();
            currentLon = d.getLongitude();
        }

        // retour au dépôt
        distance += DistanceCalculator.calculateDistance(currentLat, currentLon,
                tour.getWarehouses().getLatitude(), tour.getWarehouses().getLongitude());

        return distance;
    }


}
