package com.herve.videomail.user.Services;

import com.herve.videomail.user.Documents.User;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    User findById(ObjectId id);
    User findByLogin(String login);
    User findByLoginAndPassword(String login,String password);
    Collection<User> findAll();
    User saveOrUpdate(User user);
    void delete(ObjectId id);
}
