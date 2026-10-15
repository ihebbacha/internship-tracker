package com.iheb.tracker.service;

import com.iheb.tracker.exception.ResourceNotFoundException;
import com.iheb.tracker.model.Application;
import com.iheb.tracker.model.Status;
import com.iheb.tracker.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public List<Application> findAll(Status status, String search) {
        boolean hasSearch = search != null && !search.isBlank();

        if (status != null && hasSearch) {
            return repository.findByStatusAndCompanyContainingIgnoreCase(status, search.trim());
        }
        if (status != null) {
            return repository.findByStatus(status);
        }
        if (hasSearch) {
            return repository.findByCompanyContainingIgnoreCase(search.trim());
        }
        return repository.findAll();
    }

    public Application findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application " + id + " not found"));
    }

    public Application create(Application application) {
        application.setId(null);
        return repository.save(application);
    }

    public Application update(Long id, Application newData) {
        Application existing = findById(id);
        existing.setCompany(newData.getCompany());
        existing.setPosition(newData.getPosition());
        existing.setCity(newData.getCity());
        existing.setOfferUrl(newData.getOfferUrl());
        existing.setStatus(newData.getStatus());
        existing.setAppliedDate(newData.getAppliedDate());
        existing.setFollowUpDate(newData.getFollowUpDate());
        existing.setNotes(newData.getNotes());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Application " + id + " not found");
        }
        repository.deleteById(id);
    }
}