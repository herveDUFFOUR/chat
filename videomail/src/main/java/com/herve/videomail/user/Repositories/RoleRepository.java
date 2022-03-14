package com.herve.videomail.user.Repositories;

import com.herve.videomail.user.Documents.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    @Override
    Role insert(Role role);
}
