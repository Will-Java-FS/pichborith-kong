package com.revature.controllers;

import com.revature.models.Item;
import com.revature.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController {
    
    @Autowired
    ItemService itemService;
    
    @PostMapping
    public ResponseEntity<Item> postItem(@RequestBody Item Item) {
        Item existItem = itemService.getItemByName(Item.getName());

        if (existItem != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Item newItem = itemService.addItem(Item);
        if (newItem == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(newItem);
    }


}
