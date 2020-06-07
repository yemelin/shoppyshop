package com.example.shoppyshop.controllers;

import com.example.shoppyshop.dto.AuthRequest;
import com.example.shoppyshop.dto.UserResponseDto;
import com.example.shoppyshop.dto.UserRegisterRequestDto;
import com.example.shoppyshop.exceptions.BadRequestException;
import com.example.shoppyshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthenticationManager authManager;
    private ModelMapper modelMapper;
    private UserService userService;

    public AuthController(AuthenticationManager authManager, ModelMapper modelMapper, UserService userService) {
        this.authManager = authManager;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRegisterRequestDto udto) {
        return modelMapper.map(userService.registerNewUser(udto), UserResponseDto.class);
    }

    @PostMapping("/signin")
    public ResponseEntity authenticate(HttpServletRequest req, @Valid @RequestBody AuthRequest body) {
//        TODO: probably should move that to servcice layer, but i'm not sure - there are to many http-related things
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        Authentication auth;
        try {
            auth = authManager.authenticate(authReq);
        } catch (BadCredentialsException e){
            throw new BadRequestException("invalid username or password");
        }
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession sess = req.getSession(true);
        sess.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
        return ResponseEntity.ok().build();
    };
}
