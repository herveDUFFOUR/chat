package com.herve.videomail.SecurityService;

import com.herve.videomail.user.Documents.User;
import com.herve.videomail.user.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("loadUserByUsername methode. username : " + username);

        User user = userService.findByLogin(username);

        if (user == null) throw new UsernameNotFoundException(username + " inconnu.");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if(user.getRoles() != null && user.getRoles().size() > 0) {
            user.getRoles().forEach(role -> {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorities.add(authority);
            });
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(),true,true,true,true, grantedAuthorities.size() > 0 ? grantedAuthorities : Collections.EMPTY_LIST);
    }
}
