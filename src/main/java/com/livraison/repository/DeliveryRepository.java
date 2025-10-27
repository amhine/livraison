package com.livraison.repository;

<<<<<<< HEAD
public class DeliveryRepository {

=======
import com.livraison.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
>>>>>>> b28edf7 (LIV-25 CRUD de Tour)
}
