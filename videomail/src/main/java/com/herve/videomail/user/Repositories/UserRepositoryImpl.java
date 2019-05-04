package com.herve.videomail.user.Repositories;

import com.herve.videomail.user.Documents.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int updateLogin(String login) {
        Query query = new Query(Criteria.where(login).is(login));

        Update update = new Update();


        UpdateResult result = mongoTemplate.updateFirst(query,update, User.class);

        if(result != null){
            return (int)result.getModifiedCount();
        }
        else {
            return 0;
        }

    }
}
