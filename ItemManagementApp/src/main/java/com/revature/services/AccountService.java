package com.revature.services;

import com.revature.models.Account;
import com.revature.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;


    public Account getAccountByUsername(String username) {
        return accountRepo.findAccountByUsername(username);
    }

    @Transactional
    public Account addAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        if (!username.isBlank() && password.length() >= 3) {
            return accountRepo.save(account);
        }

        return null;
    }

    public Account getAccountById(int id) {
        return accountRepo.findById(id).orElse(null);
    }

    public Account getAccountWithItemsById(int id) {
        return accountRepo.findAccountWithItemsById(id);
    }

    public void updateAccount(Account account) {
        accountRepo.save(account);
    }
}
