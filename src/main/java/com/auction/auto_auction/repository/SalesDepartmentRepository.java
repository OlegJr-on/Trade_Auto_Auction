package com.auction.auto_auction.repository;

import com.auction.auto_auction.entity.SalesDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SalesDepartmentRepository extends JpaRepository<SalesDepartment,Integer> {

    Optional<List<SalesDepartment>> findBySalesDateBetween(LocalDateTime start, LocalDateTime end);

    Optional<List<SalesDepartment>> findBySalesDateBefore(LocalDateTime date);

    Optional<List<SalesDepartment>> findBySalesDateAfter(LocalDateTime date);
}
