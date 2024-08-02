package com.revature.services;

import com.revature.models.Item;
import com.revature.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepo itemRepo;

    public List<Item> getItems() {
        return itemRepo.findAll();
    }

    public Item getItemById(int itemId) {
        return itemRepo.findById(itemId).orElse(null);
    }

    public Item getItemByName(String name) {
        return itemRepo.findItemByName(name);
    }

    public Item addItem(Item item) {
        String name = item.getName();
        if (!name.isEmpty()) {
            return itemRepo.save(item);
        }

        return null;
    }
}
