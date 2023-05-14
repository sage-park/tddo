package com.sage.tddo.memberservice.adapter.out.persistence;

import com.netflix.discovery.converters.Auto;
import com.sage.tddo.memberservice.adapter.out.persistence.jpa.MemberJpaEntity;
import com.sage.tddo.memberservice.adapter.out.persistence.jpa.MemberJpaRepository;
import com.sage.tddo.memberservice.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    MemberRepository memberRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepository(memberJpaRepository);
    }

    @Nested
    class isExist {

        @Test
        void ifExist() {
            //given
            memberJpaRepository.save(new MemberJpaEntity(
                    "user01",
                    "김구"
            ));

            //when
            boolean exist = memberRepository.isExist("user01");

            //then
            assertThat(exist).isTrue();

        }

        @Test
        void ifNotExist() {
            //when
            boolean exist = memberRepository.isExist("user01");

            //then
            assertThat(exist).isFalse();
        }
    }

    @Nested
    class save {

        @Test
        void saveTest() {
            //given
            memberRepository.save(new Member("user01", "김구"));
            //when
            MemberJpaEntity result = memberJpaRepository.findById("user01").get();
            //then
            assertThat(result.getId()).isEqualTo("user01");
            assertThat(result.getName()).isEqualTo("김구");
        }

    }





}
