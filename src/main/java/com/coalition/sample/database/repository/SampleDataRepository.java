package com.coalition.sample.database.repository;

import com.coalition.sample.database.model.SampleData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SampleDataRepository extends CrudRepository<SampleData, UUID> {}
