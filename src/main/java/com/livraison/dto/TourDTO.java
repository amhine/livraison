package com.livraison.dto;

<<<<<<< HEAD
public class TourDTO {

=======
import java.time.LocalDate;
import java.util.List;

import com.livraison.entity.enums.OptimizerType;

import lombok.Data;

@Data
public class TourDTO {
 
	private Long id;
	private LocalDate date;
	private double distanceTotale;
    private OptimizerType optimizerUsed;
    private Long vehicleId;
    private Long warehouseId;
    private List<Long> deliveryIds;
>>>>>>> b28edf7 (LIV-25 CRUD de Tour)
}
