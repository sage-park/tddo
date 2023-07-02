package com.sage.tddo.authenticationservice.application.service;

import com.sage.tddo.authenticationservice.application.port.in.AuthenticationService;
import com.sage.tddo.authenticationservice.application.port.in.RegisterParam;
import com.sage.tddo.authenticationservice.application.port.out.LoadAuthenticationPort;
import com.sage.tddo.authenticationservice.application.port.out.SaveAuthenticationPort;
import com.sage.tddo.authenticationservice.application.port.out.RegisterEventPort;
import com.sage.tddo.authenticationservice.application.port.out.SaveUserInfoPort;
import com.sage.tddo.authenticationservice.domain.Authentication;
import com.sage.tddo.authenticationservice.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SaveAuthenticationPort saveAuthenticationPort;
    private final SaveUserInfoPort saveUserInfoPort;
    private final LoadAuthenticationPort loadAuthenticationPort;

    @Override
    public void register(RegisterParam param) {

        if (loadAuthenticationPort.isExist(param.getId())) {
            throw new UserIdAlreadyExistException();
        }

        Authentication authentication = new Authentication(param.getId(), param.getPassword(), param.isEnabled());

        saveAuthenticationPort.save(authentication);

        Member member = new Member(param.getId(), param.getName());
        saveUserInfoPort.send(member);


    }

}
