package com.livraison.service;

import com.livraison.dto.WarehouseDTO;
import com.livraison.entity.Warehouses;
import com.livraison.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO dto) {
        Warehouses warehouse = new Warehouses();
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setLatitude(dto.getLatitude());
        warehouse.setLongitude(dto.getLongitude());
        warehouse.setHeureOuverture(dto.getHeureOuverture());
        warehouse.setHeureFermeture(dto.getHeureFermeture());
        warehouseRepository.save(warehouse);
        dto.setId(warehouse.getId());
        return dto;
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto) {
        Warehouses warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setLatitude(dto.getLatitude());
        warehouse.setLongitude(dto.getLongitude());
        warehouse.setHeureOuverture(dto.getHeureOuverture());
        warehouse.setHeureFermeture(dto.getHeureFermeture());
        warehouseRepository.save(warehouse);
        return dto;
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        Warehouses warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return new WarehouseDTO(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getAddress(),
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                warehouse.getHeureOuverture(),
                warehouse.getHeureFermeture()
        );
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(w -> new WarehouseDTO(
                        w.getId(),
                        w.getName(),
                        w.getAddress(),
                        w.getLatitude(),
                        w.getLongitude(),
                        w.getHeureOuverture(),
                        w.getHeureFermeture()
                ))
                .collect(Collectors.toList());
    }
}
