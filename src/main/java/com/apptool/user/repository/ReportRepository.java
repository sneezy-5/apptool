package com.apptool.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apptool.user.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
//  @Query("SELECT r FROM Report r LEFT JOIN FETCH r.files WHERE r.id = :reportId")
//     Optional<Report> findByIdWithFiles(@Param("reportId") Long reportId);

}
