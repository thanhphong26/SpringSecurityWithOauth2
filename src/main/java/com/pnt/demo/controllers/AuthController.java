package com.pnt.demo.controllers;

import com.pnt.demo.dtos.UserLoginDTO;
import com.pnt.demo.responses.LoginResponse;
import com.pnt.demo.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private final IUserService userService;
    @PostMapping("${api.prefix}/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated UserLoginDTO userLoginDTO) throws Exception {
        String accessToken = userService.login(userLoginDTO.phone_number(), userLoginDTO.password());
        return ResponseEntity.ok(new LoginResponse(accessToken, 3600L));
    }
}
