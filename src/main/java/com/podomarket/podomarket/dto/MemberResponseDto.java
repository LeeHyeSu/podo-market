package com.podomarket.podomarket.dto;

import com.podomarket.podomarket.domain.Member;
import com.podomarket.podomarket.domain.Role;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private Long memberId;
    private String phoneNumber;
    private String nickname;
    private String location;
    private LocalDateTime createdDate;
    private Integer rating;
    private Role role;

    public MemberResponseDto(Member entity) {
        this.memberId = entity.getMemberId();
        this.phoneNumber = entity.getPhoneNumber();
        this.nickname = entity.getNickname();
        this.location = entity.getLocation();
        this.createdDate = entity.getCreatedDate();
        this.rating = entity.getRating();
        this.role = entity.getRole();
    }
}
