package com.sage.tddo.memberservice.adapter.out.persistence;

import com.sage.tddo.memberservice.adapter.out.persistence.jpa.MemberJpaEntity;
import com.sage.tddo.memberservice.adapter.out.persistence.jpa.MemberJpaRepository;
import com.sage.tddo.memberservice.application.port.out.LoadMemberPort;
import com.sage.tddo.memberservice.application.port.out.SaveMemberPort;
import com.sage.tddo.memberservice.domain.Member;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Transactional
public class MemberRepository implements LoadMemberPort, SaveMemberPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean isExist(String id) {
        return memberJpaRepository.existsById(id);
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(new MemberJpaEntity(member.getId(), member.getName()));

    }
}
