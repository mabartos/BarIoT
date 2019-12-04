package org.bariot.backend.service.auth.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.auth.BarIOTUserDetailsService;
import org.bariot.backend.service.auth.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    private AuthenticationManager authManager;
    private BarIOTUserDetailsService userDetailsService;

    @Autowired
    void setAllDependencies(AuthenticationManager authManager, BarIOTUserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authManager.authenticate(auth);
        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }

    @Override
    public String getBasicToken(String username, String password) {
        if (username == null || password == null)
            return null;

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    @Override
    public String getAuthJson(String username, String password, UserModel user) {
        if (user == null)
            return null;
        String token = "{\"token\":\"" + getBasicToken(username, password) + "\",\n";
        try {
            token += "\"user\":" + new ObjectMapper().writeValueAsString(user) + "}";

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

}
