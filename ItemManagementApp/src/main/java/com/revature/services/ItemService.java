package com.revature.services;

import com.revature.models.Item;
import com.revature.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Item updateItem(int itemId, Item item) {
        Item existItem = itemRepo.findById(itemId).orElse(null);

        if (existItem == null) {
            return null;
        }

        existItem.setName(item.getName());
        existItem.setQuantity(item.getQuantity());
        itemRepo.save(existItem);

        return existItem;
    }

    public List<Item> getItemsByUserId(int userId) {
//        List<Item> items = new ArrayList<>();
//        items.addAll(itemRepo.findItemsByUserId(userId));
//        return items;
        
        return new ArrayList<>(itemRepo.findItemsByUserId(userId));
    }

    public void deleteItemById(int itemId) {
//        Item existItem = itemRepo.findById(itemId).orElse(null);
//
//        if (existItem != null) {
//            itemRepo.delete(existItem);
//        }

        itemRepo.findById(itemId)
                .ifPresent(existItem -> itemRepo.delete(existItem));
    }
}
