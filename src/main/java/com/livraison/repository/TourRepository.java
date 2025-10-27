package com.livraison.repository;

<<<<<<< HEAD
public class TourRepository {

=======
import com.livraison.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
>>>>>>> b28edf7 (LIV-25 CRUD de Tour)
}
