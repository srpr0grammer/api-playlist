package com.apiplaylist.service;

import com.apiplaylist.models.dto.AuthetinticationDTO;
import com.apiplaylist.models.dto.LoginResponseDTO;
import com.apiplaylist.models.dto.RegisterDTO;
import com.apiplaylist.models.dto.UserResponse;
import com.apiplaylist.models.entity.User;
import com.apiplaylist.models.enums.UserRole;
import com.apiplaylist.repository.UserRepository;
import com.apiplaylist.security.TokenService;
import com.apiplaylist.service.exception.BadRequestException;
import com.apiplaylist.service.exception.ForbiddenException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.Collection;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }
    public LoginResponseDTO login(@RequestBody @Valid AuthetinticationDTO data){
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.userName(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        var loginRes = new LoginResponseDTO();
        loginRes.setToken(token);
        return loginRes;
    }

    public UserResponse register(RegisterDTO registerDto) {
        if (this.userRepository.findByUsername(registerDto.userName()) != null ) {
            throw new BadRequestException("O nome de usuário já está em uso.");
        }

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        UserRole finalRole;
        if (registerDto.role().equals(UserRole.ADMIN) && !isAdmin) {
            throw new ForbiddenException("Você não tem permissão para se registrar ou registrar alguém como ADMIN.");
        }
        else if (registerDto.role().equals(UserRole.ADMIN) && isAdmin) {
            finalRole = UserRole.ADMIN;
        } else {
            finalRole = UserRole.USER;
        }

        var encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        var newUser = new User(registerDto.userName(), encryptedPassword, registerDto.role());
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));

        var savedUser = this.userRepository.save(newUser);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(savedUser.getUsername());
        userResponse.setRole(savedUser.getUserRole());

        return userResponse;
    }
}
