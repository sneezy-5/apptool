package com.apptool.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apptool.user.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>  {
    
    @Query("SELECT f FROM File f WHERE f.report.id = :reportId")
    List<File> findByReportId(@Param("reportId") Long reportId);
    // List<File> findByReportId(Long reportId);

}
