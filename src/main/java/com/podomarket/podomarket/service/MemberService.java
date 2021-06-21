package com.podomarket.podomarket.service;

import com.podomarket.podomarket.domain.Member;
import com.podomarket.podomarket.domain.MemberRepository;
import com.podomarket.podomarket.dto.MemberResponseDto;
import com.podomarket.podomarket.dto.MemberSaveRequestDto;
import com.podomarket.podomarket.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        if(checkPhoneNumber(requestDto.getPhoneNumber()) == null) {
            return memberRepository.save(requestDto.toEntity()).getMemberId();
        }
        return null;
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElse(null);

        if (member != null) {
            member.update(requestDto.getNickname());
            return id;
        }
        return null;
    }

    @Transactional
    public Long delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElse(null);

        if (member != null) {
            memberRepository.delete(member);
            return id;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElse(null);

        return member != null ? new MemberResponseDto(member) : null;
    }

    @Transactional(readOnly = true)
    public Long checkPhoneNumber(String phoneNumber) {
        Member member = memberRepository.checkPhoneNumber(phoneNumber);

        return member != null ? member.getMemberId() : null;
    }

}
