package com.mingyang.busmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mingyang.busmanagementsystem.model.entity.RouteStop;
import jakarta.persistence.LockModeType;

public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {

    List<RouteStop> findByBusRouteId(Long routeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT rs FROM RouteStop rs WHERE rs.id = :id")
    Optional<RouteStop> findByIdWithPessimisticLock(@Param("id") Long id);

}
