package com.revature.controllers;

import com.revature.models.Account;
import com.revature.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    AccountService accountService;

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
}
