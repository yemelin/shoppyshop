package com.example.shoppyshop.service;

import com.example.shoppyshop.dto.UserRegisterRequestDto;
import com.example.shoppyshop.exceptions.AlreadyExistsException;
import com.example.shoppyshop.models.User;
import com.example.shoppyshop.models.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepo;
    private PasswordEncoder passEncoder;

    public UserService(UserRepository userRepo) {
        this.passEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.userRepo = userRepo;
    }

    public User registerNewUser(UserRegisterRequestDto udto) {
        if (userRepo.existsByUsername(udto.getUsername())){
            throw new AlreadyExistsException();
        }
        User user = User.builder()
                .password(passEncoder.encode(udto.getPassword()))
                .username(udto.getUsername())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("0000000");
        return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username + " not found"));
    }
}
