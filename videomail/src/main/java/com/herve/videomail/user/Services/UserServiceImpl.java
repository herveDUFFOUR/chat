package com.herve.videomail.user.Services;

import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service(value = "UserService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findById(ObjectId id){
       return  userRepository.findById(id).get();
    }

    @Override
    public User findByLogin(String login){
        return userRepository.findCustomByLogin(login);
    }

    @Override
    public User findByLoginAndPassword(String login,String password){
        User user = findByLogin(login);

        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return user;
        }

        return null;
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
    public User modify(ObjectId id,User user){
        User u = findById(id);
        u.setLogin(user.getLogin());
        u.setName(user.getName());
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(u);
    }


    @Override
    @Transactional(readOnly = false)
    public User saveOrUpdate(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(ObjectId id){
        userRepository.delete(findById(id));
    }
}
