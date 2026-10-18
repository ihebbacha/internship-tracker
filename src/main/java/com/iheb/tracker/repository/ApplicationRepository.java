package com.iheb.tracker.repository;

import com.iheb.tracker.model.Application;
import com.iheb.tracker.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserEmail(String email);

    List<Application> findByUserEmailAndStatus(String email, Status status);

    List<Application> findByUserEmailAndCompanyContainingIgnoreCase(String email, String company);

    List<Application> findByUserEmailAndStatusAndCompanyContainingIgnoreCase(String email, Status status, String company);

    Optional<Application> findByIdAndUserEmail(Long id, String email);
}