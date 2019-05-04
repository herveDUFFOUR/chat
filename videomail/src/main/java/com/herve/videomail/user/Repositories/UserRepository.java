package com.herve.videomail.user.Repositories;

import com.herve.videomail.user.Documents.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, ObjectId> , UserRepositoryCustom {

    @Query("{login:'?0'}")
    User findCustomByLogin(String login);
}
