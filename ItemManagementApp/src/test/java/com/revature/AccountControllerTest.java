package com.revature;

import com.revature.controllers.AccountController;
import com.revature.models.Account;
import com.revature.services.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    public AccountControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginSuccessfulTest() {
        String username = "testuser";
        String password = "testpassword";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(account);

        ResponseEntity<Account> response = accountController.loginAccount(account);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void loginFailure() {
        String username = "testuser";
        String password = "wrongpassword";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(null);

        ResponseEntity<Account> response = accountController.loginAccount(account);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void RegisterSuccessfulTest() {
        String username = "testuser";
        String password = "testpassword";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(null);
        when(accountService.addAccount(account)).thenReturn(account);

        ResponseEntity<Account> response = accountController.registerAccount(account);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void RegisterDuplicateAccountTest() {
        String username = "testuser";
        String password = "testpassword";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(account);


        ResponseEntity<Account> response = accountController.registerAccount(account);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void RegisterWithBadInputTest() {
        String username = "";
        String password = "1";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(null);
        when(accountService.addAccount(account)).thenReturn(null);

        ResponseEntity<Account> response = accountController.registerAccount(account);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
