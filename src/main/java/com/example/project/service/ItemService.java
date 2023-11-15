package com.example.project.service;

import com.example.project.domain.Item;
import com.example.project.dto.item.AddItemRequest;
import com.example.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

//    public Item save(AddItemRequest request, )
}
