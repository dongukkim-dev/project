package com.example.project.controller;

import com.example.project.dto.cart.CartDto;
import com.example.project.dto.cart.CartResponse;
import com.example.project.service.ItemService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartApiController {

    //localstorage 에 장바구니에 담길 상품 정보를 저장해서 get 요청만 있으면 되나?

    private final ItemService itemService;

    /**
     *  GET 요청으로 (item_id, amount)를 보내오면 상품 정보(name, price)를 보내준다.
     */
    @PostMapping("/api/carts")
    public ResponseEntity<List<CartResponse>> findCartItems(@RequestBody List<CartDto> cartData) {

        String email = SecurityUtil.getCurrentUsername();

        List<CartResponse> resCart = cartData.stream()
                .map(id -> itemService.findCartById(id.getItem_id(), id.getAmount()))
                .toList();

        log.info("resCart에 담긴 정보, name = {}, price = {}", resCart.get(0).getName(), resCart.get(0).getPrice());

        return ResponseEntity.ok()
                .body(resCart);
    }
}
