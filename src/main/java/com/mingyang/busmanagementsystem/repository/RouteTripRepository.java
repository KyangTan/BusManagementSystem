package com.mingyang.busmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingyang.busmanagementsystem.model.entity.RouteTrip;

public interface RouteTripRepository extends JpaRepository<RouteTrip, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT rt FROM RouteTrip rt WHERE rt.id = :id")
    Optional<RouteTrip> findByIdWithPessimisticLock(@Param("id") Long id);

}
