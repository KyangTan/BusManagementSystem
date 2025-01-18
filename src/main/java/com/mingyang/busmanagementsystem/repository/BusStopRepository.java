package com.mingyang.busmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mingyang.busmanagementsystem.model.entity.BusStop;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface BusStopRepository extends JpaRepository<BusStop, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BusStop b WHERE b.id = :id")
    Optional<BusStop> findByIdWithPessimisticLock(@Param("id") Long id);

    @Query("SELECT b FROM BusStop b WHERE " +
           "LOWER(b.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(b.shortName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(b.address) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<BusStop> findByNameContainingIgnoreCase(@Param("searchTerm") String searchTerm);
}
