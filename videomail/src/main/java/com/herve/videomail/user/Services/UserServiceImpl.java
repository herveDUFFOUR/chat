package com.herve.videomail.user.Services;

import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service(value = "UserService")
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(ObjectId id){
       return  userRepository.findById(id).get();
    }

    @Override
    public User findByLogin(String login){
        logger.info("findByLogin.login :" + login);

        return userRepository.findCustomByLogin(login);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(String login){
        userRepository.delete(findByLogin(login));
    }

    @Override
    @Transactional(readOnly = false)
    public User modify(ObjectId id,User user){
        User u = findById(id);
        u.setLogin(user.getLogin());
        u.setName(user.getName());
        u.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(u);
    }


    @Override
    @Transactional(readOnly = false)
    public User saveOrUpdate(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ObjectId id){
        userRepository.delete(findById(id));
    }
}
