package com.sbp_it_support.service;

import com.sbp_it_support.dto.CreateProviderRequest;
import com.sbp_it_support.entity.Provider;
import com.sbp_it_support.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public Provider createProvider(CreateProviderRequest request) {
        return providerRepository.save(new Provider(request.getName()));
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
}
