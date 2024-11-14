package com.lms.security;

import com.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * Configure Service used by Spring under the hood
 * Spring requires loadUserByUsername(•) to be Overridden from implemented class
 * Some portions of auth require a org.springframework.security.core.userdetails.User
 * instead of custom User Model.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;

    /**
     * Finds User from user repository or throws exceptions.
     *
     * @return cast from User to core.userdetails.User
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        com.lms.model.User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(
                user.getEmail(), user.getPassword(), new HashSet<GrantedAuthority>());
    }
}
