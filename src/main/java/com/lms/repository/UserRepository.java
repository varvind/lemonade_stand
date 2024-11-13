package com.lms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.lms.model.User;

public interface UserRepository extends MongoRepository<User, String>  {

    boolean existsUserByEmail(String email);
}

