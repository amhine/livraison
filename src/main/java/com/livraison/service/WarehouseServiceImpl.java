package com.livraison.service;

import com.livraison.dto.WarehouseDTO;
import com.livraison.entity.Warehouses;
import com.livraison.mapper.WarehouseMapper;
import com.livraison.repository.WarehouseRepository;

import java.util.List;
import java.util.stream.Collectors;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO dto) {
        Warehouses entity = WarehouseMapper.toEntity(dto);
        Warehouses saved = warehouseRepository.save(entity);
        return WarehouseMapper.toDTO(saved);
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto) {
        Warehouses existing = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setHeureOuverture(dto.getHeureOuverture());
        existing.setHeureFermeture(dto.getHeureFermeture());
        Warehouses updated = warehouseRepository.save(existing);
        return WarehouseMapper.toDTO(updated);
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        Warehouses entity = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
        return WarehouseMapper.toDTO(entity);
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
