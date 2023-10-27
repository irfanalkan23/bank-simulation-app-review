package com.cydeo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {
    //why extending UserDetailsService? because it has its own implementation
    //I'll override it and create my own authentication structure
    //I'll create a converter class to convert my own user entity and returns UserDetails (which loadByUserName(String username) method returns)
}
