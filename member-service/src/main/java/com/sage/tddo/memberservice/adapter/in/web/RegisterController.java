package com.sage.tddo.memberservice.adapter.in.web;

import com.sage.tddo.memberservice.application.port.in.UserRegisterUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserRegisterUsecase userRegisterUsecase;

    @PostMapping("/member")
    public ResponseEntity postMember(
            @RequestBody PostMemberRequest request
    ) {

        try {
            userRegisterUsecase.register(request.getId(), request.getName());
            return ResponseEntity.created(new URI("")).build();
        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
