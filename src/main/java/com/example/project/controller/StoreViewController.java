package com.example.project.controller;

import com.example.project.domain.Store;
import com.example.project.dto.store.StoreListViewResponse;
import com.example.project.dto.store.StoreViewResponse;
import com.example.project.service.StoreService;
import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreViewController {

    private final StoreService storeService;
    private final UserService userService;

    @GetMapping("/stores")
    public String getStores(Model model) {
        List<StoreListViewResponse> stores = storeService.findAll()
                .stream()
                .map(StoreListViewResponse::new)
                .toList();
        //articleList 페이지에서 item 목록을 불러올 때 사용
        model.addAttribute("stores", stores);

        //여기서 목록을 받아올 때 로그인 사용자 이름도 같이 받아오면 됨 or session 사용

        return "storeList";
    }

    @GetMapping("/stores/{id}")
    public String getStore(@PathVariable Long id, Model model) {
        Store store = storeService.findById(id);
        model.addAttribute("store", new StoreViewResponse(store));

        return "store";
    }


    @GetMapping("/new-store")
    public String newStore(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("store", new StoreViewResponse());
        } else {
            Store store = storeService.findById(id);
            model.addAttribute("store", new StoreViewResponse(store));
        }

        return "newStore";
    }
}
