package com.sage.tddo.memberservice.application.service;

import com.sage.tddo.memberservice.application.port.in.UserRegisterUsecase;
import com.sage.tddo.memberservice.application.port.out.LoadMemberPort;
import com.sage.tddo.memberservice.application.port.out.SaveMemberPort;
import com.sage.tddo.memberservice.domain.Member;
import com.sage.tddo.memberservice.exception.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisterUsecaseImplTest {

    UserRegisterUsecase memberRegisterUsecase;
    @Mock
    private LoadMemberPort loadMemberPort;
    @Mock
    private SaveMemberPort saveMemberPort;

    @BeforeEach
    void setUp() {
        memberRegisterUsecase = new UserRegisterUsecaseImpl(loadMemberPort, saveMemberPort);
    }

    /**
     * 만약 이미 있으면 에러를 던진다.
     */
    @Test
    void ifAlreadyExist() {
        //given
        given(loadMemberPort.isExist(any())).willReturn(true);
        //when
        Exception exception = catchException(() -> memberRegisterUsecase.register("user01", "김구"));
        //then
        assertThat(exception).isInstanceOf(UserAlreadyExistException.class);

    }

    /**
     * 만약 유저가 없으면 유저를 저장한다.
     */
    @Test
    void ifNotExist() {
        //given
        given(loadMemberPort.isExist(any())).willReturn(false);
        //when
        memberRegisterUsecase.register("user01", "김구");
        //then
        then(saveMemberPort).should().save(refEq(new Member("user01", "김구")));

    }




}
