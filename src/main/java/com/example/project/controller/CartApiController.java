package com.example.project.controller;

import com.example.project.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartApiController {

    //localstorage 에 장바구니에 담길 상품 정보를 저장해서 get 요청만 있으면 되나?
}
