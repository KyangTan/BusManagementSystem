package com.mingyang.busmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mingyang.busmanagementsystem.model.entity.PolylinePlot;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface PolylinePlotRepository extends JpaRepository<PolylinePlot, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PolylinePlot p WHERE p.id = :id")
    Optional<PolylinePlot> findByIdWithPessimisticLock(@Param("id") Long id);
}
