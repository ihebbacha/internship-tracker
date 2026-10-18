package com.iheb.tracker.service;

import com.iheb.tracker.dto.ApplicationRequest;
import com.iheb.tracker.dto.ApplicationResponse;
import com.iheb.tracker.exception.InvalidCredentialsException;
import com.iheb.tracker.exception.ResourceNotFoundException;
import com.iheb.tracker.model.AppUser;
import com.iheb.tracker.model.Application;
import com.iheb.tracker.model.Status;
import com.iheb.tracker.repository.AppUserRepository;
import com.iheb.tracker.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final AppUserRepository userRepository;

    public ApplicationService(ApplicationRepository repository, AppUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<ApplicationResponse> findAll(String email, Status status, String search) {
        boolean hasSearch = search != null && !search.isBlank();
        List<Application> applications;

        if (status != null && hasSearch) {
            applications = repository.findByUserEmailAndStatusAndCompanyContainingIgnoreCase(email, status, search.trim());
        } else if (status != null) {
            applications = repository.findByUserEmailAndStatus(email, status);
        } else if (hasSearch) {
            applications = repository.findByUserEmailAndCompanyContainingIgnoreCase(email, search.trim());
        } else {
            applications = repository.findByUserEmail(email);
        }

        return applications.stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    public ApplicationResponse findById(Long id, String email) {
        return ApplicationResponse.from(getOwnedApplication(id, email));
    }

    public ApplicationResponse create(ApplicationRequest request, String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Application application = new Application();
        copyFields(request, application);
        application.setUser(user);

        return ApplicationResponse.from(repository.save(application));
    }

    public ApplicationResponse update(Long id, ApplicationRequest request, String email) {
        Application application = getOwnedApplication(id, email);
        copyFields(request, application);
        return ApplicationResponse.from(repository.save(application));
    }

    public void delete(Long id, String email) {
        repository.delete(getOwnedApplication(id, email));
    }

    private Application getOwnedApplication(Long id, String email) {
        return repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Application " + id + " not found"));
    }

    private void copyFields(ApplicationRequest request, Application application) {
        application.setCompany(request.company().trim());
        application.setPosition(request.position().trim());
        application.setCity(request.city());
        application.setOfferUrl(request.offerUrl());
        application.setStatus(request.status());
        application.setAppliedDate(request.appliedDate());
        application.setFollowUpDate(request.followUpDate());
        application.setNotes(request.notes());
    }
}