package com.lms.repository;

import com.lms.model.Networth;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NetworthRepository extends MongoRepository<Networth, String> {
}
