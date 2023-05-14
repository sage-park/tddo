package com.sage.tddo.authenticationservice.adapter.in.web;

import com.sage.tddo.authenticationservice.application.port.in.AuthenticationService;
import com.sage.tddo.authenticationservice.application.port.in.RegisterParam;
import com.sage.tddo.authenticationservice.application.service.UserIdAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sage.tddo.authenticationservice.adapter.in.web.ErrorCode.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){

        if (!StringUtils.hasLength(request.getId()))
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ERROR_002, "id is required"));
        if (!StringUtils.hasLength(request.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ERROR_002, "password is required"));
        }
        if (!StringUtils.hasLength(request.getName())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ERROR_002, "name is required"));
        }


        try {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            authenticationService.register(new RegisterParam(request.getId(), encodedPassword, request.getName()));
        } catch (UserIdAlreadyExistException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ERROR_001, "Username is already taken."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.created(null).build();
    }

}
