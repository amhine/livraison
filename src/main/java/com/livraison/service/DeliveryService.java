package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import java.util.List;

public interface DeliveryService {

    List<DeliveryDTO> getAllDeliveries();
    DeliveryDTO getDeliveryById(Long id);
    DeliveryDTO createDelivery(DeliveryDTO deliveryDTO);
    DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO);
    void deleteDelivery(Long id);
}
