package com.sbp_it_support.controller;

import com.sbp_it_support.dto.CreateProviderRequest;
import com.sbp_it_support.entity.Provider;
import com.sbp_it_support.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@CrossOrigin(origins = "*")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @PostMapping
    public ResponseEntity<Provider> createProvider(@RequestBody CreateProviderRequest request) {
        Provider provider = providerService.createProvider(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(provider);
    }

    @GetMapping
    public ResponseEntity<List<Provider>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }
}
