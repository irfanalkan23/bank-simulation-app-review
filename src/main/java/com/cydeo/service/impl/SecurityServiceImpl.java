package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    //Normally we need to communicate with the Service, not Repository, when we are communicating with another service,
    // but this is an exception now, because we only need once.
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //we need to get our OWN user from database. How?
        User user = userRepository.findByUsername(username);

        //return some exception if user doesn't exist
        if (user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }

        //return user information in as a UserDetails
        return new UserPrincipal(user);
    }




















}
