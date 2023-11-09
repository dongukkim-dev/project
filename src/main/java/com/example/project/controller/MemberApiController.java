//package com.example.project.controller;
//
//import com.example.project.domain.User;
//import com.example.project.dto.MemberDto;
//import com.example.project.service.UserService;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//public class MemberApiController {
//
//    private final UserService memberService;
//
//    @GetMapping("/api/members")
//    public Result member() {
//        List<User> findMembers = memberService.findMembers();
//        List<MemberDto> collect = findMembers.stream()
//                .map(m -> new MemberDto(m.getName()))
//                .collect(Collectors.toList());
//
//        return new Result(collect);
//    }
//
////    @PostMapping("/api/members")
////    public TokenInfo login(@RequestBody)
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T data;
//    }
//}
