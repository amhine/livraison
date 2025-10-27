package com.livraison.service;

<<<<<<< HEAD
public class TourService {

=======
import com.livraison.dto.TourDTO;

import java.util.List;

public interface TourService {
    List<TourDTO> findAll();
    TourDTO findById(Long id);
    TourDTO save(TourDTO dto);
    TourDTO update(Long id, TourDTO dto);
    void delete(Long id);
>>>>>>> b28edf7 (LIV-25 CRUD de Tour)
}
