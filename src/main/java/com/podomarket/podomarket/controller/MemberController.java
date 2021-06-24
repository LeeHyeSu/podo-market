package com.podomarket.podomarket.controller;

import com.podomarket.podomarket.dto.MemberResponseDto;
import com.podomarket.podomarket.dto.MemberSaveRequestDto;
import com.podomarket.podomarket.dto.MemberUpdateRequestDto;
import com.podomarket.podomarket.jwt.JwtUtil;
import com.podomarket.podomarket.service.MemberService;
import com.podomarket.podomarket.service.SmsCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final SmsCertificationService smsCertificationService;

    @PostMapping("/register")
    public Long register(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @GetMapping("/login")
    public Long login(String phoneNumber, HttpServletResponse response) {
        Long memberId = memberService.checkPhoneNumber(phoneNumber);

        if (memberId != null) {
            String token = jwtUtil.generateToken(memberId);
            response.setHeader("Authorization", token);
            System.out.println("token : " + token);
        }

        return memberId;
    }

    @GetMapping("/members/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @PutMapping("/members/{id}")
    public Long updateInfo(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(id, requestDto);
    }

    @DeleteMapping("/members/{id}")
    public Long delete(@PathVariable Long id) {
        return memberService.delete(id);
    }

    @ResponseBody
    @GetMapping("/sendSms")
    public String sendSms(String phoneNumber) {
        String authenticationNumber = String.valueOf((int)(Math.random() * 8999) + 1000);

        System.out.println("수신자 번호: " + phoneNumber);
        System.out.println("인증번호: " + authenticationNumber);

        smsCertificationService.sendMessage(phoneNumber, authenticationNumber);
        return authenticationNumber;
    }

}
