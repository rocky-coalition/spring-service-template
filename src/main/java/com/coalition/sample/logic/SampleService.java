package com.coalition.sample.logic;

import com.coalition.core.configuration.error.logic.LogicException;
import com.coalition.sample.database.model.SampleData;
import com.coalition.sample.database.repository.SampleDataRepository;
import com.coalition.sample.integration.api.SampleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SampleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleService.class);
    private SampleDataRepository sampleDataRepository;
    private SampleClient sampleClient;

    public SampleService (@Autowired SampleDataRepository sampleDataRepository) {
        this.sampleDataRepository = sampleDataRepository;
    }

    @Cacheable(cacheNames = "sample-data-cache")
    public SampleData getSampleData(String id) throws LogicException {
        try {
            return this.sampleDataRepository.findById(UUID.fromString(id)).get();
        } catch (Exception e) {
            LOGGER.debug("Error while retrieving sample data for id {}", id, e);
            throw new LogicException("Error while retrieving sample data.", e);
        }
    }
}
