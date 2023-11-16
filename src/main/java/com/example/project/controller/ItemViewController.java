package com.example.project.controller;

import com.example.project.domain.Item;
import com.example.project.domain.Store;
import com.example.project.dto.item.ItemListViewResponse;
import com.example.project.dto.item.ItemViewResponse;
import com.example.project.dto.store.StoreListViewResponse;
import com.example.project.dto.store.StoreViewResponse;
import com.example.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemViewController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String getItems(Model model) {
        List<ItemListViewResponse> items = itemService.findAll()
                .stream()
                .map(ItemListViewResponse::new)
                .toList();
        //articleList 페이지에서 item 목록을 불러올 때 사용
        model.addAttribute("items", items);

        //여기서 목록을 받아올 때 로그인 사용자 이름도 같이 받아오면 됨 or session 사용

        return "itemList";
    }

    @GetMapping("/items/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", new ItemViewResponse(item));

        return "item";
    }


    @GetMapping("/new-item")
    public String newItem(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("item", new ItemViewResponse());
        } else {
            Item item = itemService.findById(id);
            model.addAttribute("item", new ItemViewResponse(item));
        }

        return "newItem";
    }
}
