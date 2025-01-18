package com.mingyang.busmanagementsystem.repository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import com.mingyang.busmanagementsystem.model.entity.TripRecord;

public interface TripRecordRepository extends JpaRepository<TripRecord, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT tr FROM TripRecord tr WHERE tr.id = :id")
    Optional<TripRecord> findByIdWithPessimisticLock(@Param("id") Long id);

    @Query("SELECT tr FROM TripRecord tr WHERE " +
           "(:busRouteId IS NULL OR tr.busRoute.id = :busRouteId) AND " +
           "(:busId IS NULL OR tr.bus.id = :busId) AND " +
           "(:tripStatus IS NULL OR tr.tripStatus = :tripStatus) AND " +
           "(:startTime IS NULL OR tr.startTime >= :startTime) AND " +
           "(:endTime IS NULL OR tr.startTime <= :endTime)")
    List<TripRecord> findByFilters(
            @Param("busRouteId") Long busRouteId,
            @Param("busId") Long busId,
            @Param("tripStatus") String tripStatus,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    TripRecord findByBusIdAndEndTimeIsNull(Long busId);
}
