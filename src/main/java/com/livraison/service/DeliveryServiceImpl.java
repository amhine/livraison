package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.Delivery;
import com.livraison.mapper.DeliveryMapper;
import com.livraison.repository.DeliveryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll()
                .stream()
                .map(deliveryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));
        return deliveryMapper.toDTO(delivery);
    }

    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        return deliveryMapper.toDTO(deliveryRepository.save(delivery));
    }

    @Override
    public DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery existing = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));

        existing.setNameClient(deliveryDTO.getNameClient());
        existing.setAddress(deliveryDTO.getAddress());
        existing.setLatitude(deliveryDTO.getLatitude());
        existing.setLongitude(deliveryDTO.getLongitude());
        existing.setPoids(deliveryDTO.getPoids());
        existing.setVolume(deliveryDTO.getVolume());
        existing.setStatus(deliveryDTO.getStatus());

        if (deliveryDTO.getTourId() != null) {
            existing.getTour().setId(deliveryDTO.getTourId());
        } else {
            existing.setTour(null);
        }

        return deliveryMapper.toDTO(deliveryRepository.save(existing));
    }

    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
