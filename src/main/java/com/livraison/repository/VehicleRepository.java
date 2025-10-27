package com.livraison.repository;

<<<<<<< HEAD
public class VehicleRepository {

=======
import com.livraison.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
>>>>>>> b28edf7 (LIV-25 CRUD de Tour)
}
