package com.iheb.tracker.controller;

import com.iheb.tracker.dto.ApplicationRequest;
import com.iheb.tracker.dto.ApplicationResponse;
import com.iheb.tracker.model.Status;
import com.iheb.tracker.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<ApplicationResponse> getAll(@RequestParam(required = false) Status status,
                                            @RequestParam(required = false) String search,
                                            Authentication authentication) {
        return service.findAll(authentication.getName(), status, search);
    }

    @GetMapping("/{id}")
    public ApplicationResponse getOne(@PathVariable Long id, Authentication authentication) {
        return service.findById(id, authentication.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(@Valid @RequestBody ApplicationRequest request,
                                      Authentication authentication) {
        return service.create(request, authentication.getName());
    }

    @PutMapping("/{id}")
    public ApplicationResponse update(@PathVariable Long id,
                                      @Valid @RequestBody ApplicationRequest request,
                                      Authentication authentication) {
        return service.update(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, Authentication authentication) {
        service.delete(id, authentication.getName());
    }
}