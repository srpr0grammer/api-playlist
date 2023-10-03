package com.apiplaylist.controller;

import com.apiplaylist.models.dto.AuthetinticationDTO;
import com.apiplaylist.models.dto.LoginResponseDTO;
import com.apiplaylist.models.dto.RegisterDTO;
import com.apiplaylist.models.dto.UserResponse;
import com.apiplaylist.service.AuthorizationService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthetinticationDTO authetinticationDto){
        var loginResponse = authorizationService.login(authetinticationDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterDTO registerDto) {
        var userResponse = authorizationService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
