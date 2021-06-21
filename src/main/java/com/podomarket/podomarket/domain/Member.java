package com.podomarket.podomarket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(Long memberId, String phoneNumber, String nickname, String location, LocalDateTime createdDate, Integer rating, Role role) {
        this.memberId = memberId;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.location = location;
        this.createdDate = createdDate;
        this.rating = rating;
        this.role = role;
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }

}
