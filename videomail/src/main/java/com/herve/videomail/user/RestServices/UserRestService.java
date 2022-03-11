package com.herve.videomail.user.RestServices;

import com.herve.videomail.token.JwtRequest;
import com.herve.videomail.token.JwtResponse;
import com.herve.videomail.user.Documents.User;
import com.herve.videomail.SecurityService.JwtUserDetailsService;
import com.herve.videomail.user.Services.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin
public class UserRestService {

    private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/auth/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception{

        logger.info("createAuthenticationToken");
        logger.info("request : " + request.toString());

        //authenticate(request.getUsername(),request.getPassword());

        //final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());

        //final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable(value = "login") String login){
        logger.info("findByLogin service OK");
        return new ResponseEntity<User>(userService.findByLogin(login),HttpStatus.OK);

    }



    @GetMapping("/user/users")
    public ResponseEntity<Collection<User>> findAll(){
        logger.info("findAll service OK");
        return new ResponseEntity<Collection<User>>( userService.findAll(),HttpStatus.OK);
    }


    @DeleteMapping("/user/delete/{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable(value="id") ObjectId id){
        logger.info("delete service OK");
        userService.delete(id);
        return new ResponseEntity<String>("User Deleted",HttpStatus.OK);
    }

    @PutMapping(path="/user/modify/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<User> modify(@PathVariable(value="id")ObjectId id, @RequestBody User user){
        logger.info("modify service OK");
        return new ResponseEntity<User>(userService.modify(id,user),HttpStatus.OK);
    }

    @PostMapping(path="/user/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<User> save(@RequestBody User user){
        logger.info("save service OK");
        User userSaved = userService.saveOrUpdate(user);
        return new ResponseEntity<User>(userSaved,HttpStatus.OK);
    }

    private void authenticate(String username,String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch(DisabledException e){
            logger.error("USER DISABLE",e);
            throw new Exception("USER DISABLE",e);
        }
        catch(BadCredentialsException e){
            logger.error("INVALID CREDENTIALS",e);
            throw new Exception("INVALID CREDENTIALS",e);
        }
    }
}
