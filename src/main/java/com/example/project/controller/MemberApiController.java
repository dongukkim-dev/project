package com.example.project.controller;

import com.example.project.dto.AddMemberRequest;
import com.example.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/user")
    public String signup(AddMemberRequest request) {
        memberService.save(request);
        return "redirect:/login";
    }
}
