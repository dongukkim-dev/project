package com.example.project.dto;

import lombok.Data;

/**
 * 관리자 페이지에서 검색 조건을 줘서 필터링을 할 수 있도록
 */
@Data
public class UserSearchCondition {
    //회원명, 회원 주문 상태?

    private String username;
    private String order_status;
}
