package com.sage.tddo.memberservice.application.service;

import com.sage.tddo.memberservice.application.port.in.UserRegisterUsecase;
import com.sage.tddo.memberservice.application.port.out.LoadMemberPort;
import com.sage.tddo.memberservice.application.port.out.SaveMemberPort;
import com.sage.tddo.memberservice.domain.Member;
import com.sage.tddo.memberservice.exception.UserAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegisterUsecaseImpl implements UserRegisterUsecase {

    private final LoadMemberPort loadMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public void register(String id, String name) {

        if (loadMemberPort.isExist(id)) {
            throw new UserAlreadyExistException();
        }

        Member member = new Member(id, name);

        saveMemberPort.save(member);

    }
}
