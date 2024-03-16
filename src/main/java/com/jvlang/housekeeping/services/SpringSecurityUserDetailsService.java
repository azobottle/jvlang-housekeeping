package com.jvlang.housekeeping.services;

import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.repo.RelationUserRoleRepository;
import com.jvlang.housekeeping.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringSecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelationUserRoleRepository relationUserRoleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(username);
        List<String> roleNames = relationUserRoleRepository.findRoleNamesByUserId(user.getId());
        user.setRoles(roleNames);
        return user;
    }
}
