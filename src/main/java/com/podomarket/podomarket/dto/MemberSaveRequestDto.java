package com.podomarket.podomarket.dto;

import com.podomarket.podomarket.domain.Member;
import com.podomarket.podomarket.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String phoneNumber;
    private String nickname;
    private String location;
    private LocalDateTime createdDate;
    private Integer rating;
    private Role role;

    @Builder
    public MemberSaveRequestDto(String phoneNumber, String nickname, String location, LocalDateTime createdDate, Integer rating, Role role) {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.location = location;
        this.createdDate = createdDate;
        this.rating = rating;
        this.role = role;
    }

    public Member toEntity() {
        return Member.builder()
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .location(location)
                .createdDate(LocalDateTime.now())
                .rating(0)
                .role(Role.MEMBER)
                .build();
    }

}
