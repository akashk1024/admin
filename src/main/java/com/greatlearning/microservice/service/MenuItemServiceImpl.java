package com.greatlearning.microservice.service;

import com.greatlearning.microservice.entity.MenuItem;
import com.greatlearning.microservice.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemServiceImpl {
    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItem findByItemId(Integer itemId) throws Exception {
        Optional<MenuItem> items = menuItemRepository.findById(itemId);

        return items.isPresent() ? items.get() : null;
    }

    public MenuItem getMenuItemById(int itemId) {
        return menuItemRepository.getById(itemId);
    }
}
