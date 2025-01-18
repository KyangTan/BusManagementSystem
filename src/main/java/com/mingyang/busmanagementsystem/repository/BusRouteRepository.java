package com.mingyang.busmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mingyang.busmanagementsystem.model.entity.BusRoute;
import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BusRoute b WHERE b.id = :id")
    Optional<BusRoute> findByIdWithPessimisticLock(@Param("id") Long id);
}
