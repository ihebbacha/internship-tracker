package com.iheb.tracker.repository;

import com.iheb.tracker.model.Application;
import com.iheb.tracker.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStatus(Status status);

    List<Application> findByCompanyContainingIgnoreCase(String company);

    List<Application> findByStatusAndCompanyContainingIgnoreCase(Status status, String company);
}