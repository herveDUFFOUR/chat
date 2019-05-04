package com.herve.videomail.user.RestServices;

import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;

@Controller
//@RestController
@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
public class UserRestService {

    private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);

    @Autowired
    UserService userService;


    @GetMapping("/")
    @ResponseBody
    public String pong(){
        logger.info("Démarrage du service OK");

        return new String("OK");
    }

    @GetMapping("/user/{login}")
    @ResponseBody
    public User findByLogin(@PathVariable(value = "login") String login){
        logger.info("findByLogin service OK");
        return userService.findByLogin(login);
    }

    @GetMapping("/user/{login}/{password}")
    @ResponseBody
    public User findByLoginAndPassword(@PathVariable(value = "login") String login,@PathVariable(value = "password") String password){
        logger.info("findByLoginAndPassword service OK");
        return  userService.findByLoginAndPassword(login,password);
    }

    @GetMapping("/users")
    @ResponseBody
    public Collection<User> findAll(){
        logger.info("findAll service OK");
        return userService.findAll();
    }

    @PostMapping(path="/saveuser",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional
    public User save(@RequestBody User user){
        logger.info("save service OK");
        User userSaved = userService.saveOrUpdate(user);
        return userSaved;
    }
}
