package com.Vechile_Service.repo;

import com.Vechile_Service.entity.VehicleVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleVisitRepository extends JpaRepository<VehicleVisit, Long> {
    List<VehicleVisit> findByVehicleCustomerCustomerId(Long customerId);
}
