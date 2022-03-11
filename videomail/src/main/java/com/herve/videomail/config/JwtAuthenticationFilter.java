package com.herve.videomail.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herve.videomail.user.Documents.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super();
        this.authenticationManager = authenticationManager;

        //setFilterProcessesUrl("/auth/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response){

        User user = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(),User.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String username = user.getLogin();
        String password = user.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response, FilterChain chain, Authentication authResult){

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();

        List<String> roles  = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretBytes = Base64.getEncoder().encode("antoinette".getBytes());
        Key signingKey = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());

        String token = Jwts.builder()
                .signWith(signatureAlgorithm,"antoinette")
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .compact();

        String jwt = JWT.create()
                .withSubject(user.getUsername())
                .withArrayClaim("roles",roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .sign(Algorithm.HMAC256("antoinette"));

        response.addHeader("Authorization",jwt);
        //response.addHeader("Access-Control-Expose-Headers","Authorization");
    }


}
