package com.greatlearning.microservice.service;

import com.greatlearning.microservice.entity.Items;
import com.greatlearning.microservice.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    public List<Items> save(List<Items> items) {
        return itemsRepository.saveAll(items);
    }
}
