package com.podomarket.podomarket.dto;

import com.podomarket.podomarket.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    private String nickname;

    @Builder
    public MemberUpdateRequestDto(String nickname) {
        this.nickname = nickname;
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .build();
    }
}
