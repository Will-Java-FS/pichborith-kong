package com.revature.controllers;

import com.revature.models.Account;
import com.revature.models.Item;
import com.revature.services.AccountService;
import com.revature.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ItemService itemService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account existAccount = accountService.getAccountByUsername(account.getUsername());

        if (existAccount != null) {
//            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Account newAccount = accountService.addAccount(account);
        if (newAccount == null) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        Account existAccount = accountService.getAccountByUsername(username);

        if (existAccount != null && password.equals(existAccount.getPassword())) {
            return ResponseEntity.ok(existAccount);
        }

//        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<Item>> viewItemsInAccount(@PathVariable int userId) {
        Account existAccount = accountService.getAccountById(userId);

        if (existAccount == null ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
        List<Item> items = itemService.getItemsByUserId(userId);
        System.out.println(items);

        return ResponseEntity.ok(items);
    }

    @PostMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Item> addItemToAccount(@PathVariable int userId, @PathVariable int itemId) {
        Account existAccount = accountService.getAccountWithItemsById(userId);
        Item existItem = itemService.getItemById(itemId);

        if (existAccount == null || existItem == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

        existAccount.addItem(existItem);
        accountService.updateAccount(existAccount);

        return ResponseEntity.ok(existItem);
    }
    
}
