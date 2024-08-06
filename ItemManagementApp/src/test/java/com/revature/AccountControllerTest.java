package com.revature;

import com.revature.controllers.AccountController;
import com.revature.models.Account;
import com.revature.models.Item;
import com.revature.services.AccountService;
import com.revature.services.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private ItemService itemService;

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

        ResponseEntity<Account> response = accountController.loginAccount(
            account);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void loginFailureTest() {
        String username = "testuser";
        String password = "wrongpassword";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(null);

        ResponseEntity<Account> response = accountController.loginAccount(
            account);

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

        ResponseEntity<Account> response = accountController.registerAccount(
            account);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void RegisterDuplicateAccountTest() {
        String username = "testuser";
        String password = "testpassword";
        Account account = new Account(username, password);

        when(accountService.getAccountByUsername(username)).thenReturn(account);

        ResponseEntity<Account> response = accountController.registerAccount(
            account);
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

        ResponseEntity<Account> response = accountController.registerAccount(
            account);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void RetrieveItemsByAccountIdSuccessfulTest() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Shirt", 10));
        items.add(new Item("Dress", 20));
        items.add(new Item("Hat", 50));
        int id = 1;
        String username = "user";
        String password = "testpassword";
        Account account = new Account(id, username, password);

        when(accountService.getAccountById(id)).thenReturn(account);
        when(itemService.getItemsByUserId(account.getId())).thenReturn(items);

        ResponseEntity<List<Item>> response = accountController.viewItemsInAccount(
            id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    @Test
    public void RetrieveItemsWithWrongAccountIdTest() {
        int id = 1;

        when(accountService.getAccountById(id)).thenReturn(null);

        ResponseEntity<List<Item>> response = accountController.viewItemsInAccount(
            id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void addItemToAccountSuccessfulTest() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Shirt", 10));
        items.add(new Item("Dress", 20));
        int userId = 1;
        String username = "user";
        String password = "testpassword";
        Account account = new Account(userId, username, password);
        account.setItems(items);
        int itemId = 3;
        Item newItem = new Item(itemId,"Hat", 50);

        when(accountService.getAccountWithItemsById(userId)).thenReturn(account);
        when(itemService.getItemById(itemId)).thenReturn(newItem);

        ResponseEntity<List<Item>> response = accountController.addItemToAccount(userId, itemId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        items.add(newItem);
        assertEquals(items, response.getBody());
    }

    @Test
    public void addDuplicateItemToAccountTest() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Shirt", 10));
        items.add(new Item("Dress", 20));
        int userId = 1;
        String username = "user";
        String password = "testpassword";
        Account account = new Account(userId, username, password);
        account.setItems(items);
        int itemId = 3;

        when(accountService.getAccountWithItemsById(userId)).thenReturn(account);
        when(itemService.getItemById(itemId)).thenReturn(items.get(1));

        ResponseEntity<List<Item>> response = accountController.addItemToAccount(userId, itemId);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    @Test
    public void addNonExistItemTest() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Shirt", 10));
        items.add(new Item("Dress", 20));
        int userId = 1;
        String username = "user";
        String password = "testpassword";
        Account account = new Account(userId, username, password);
        account.setItems(items);
        int itemId = 3;

        when(accountService.getAccountWithItemsById(userId)).thenReturn(account);
        when(itemService.getItemById(itemId)).thenReturn(null);

        ResponseEntity<List<Item>> response = accountController.addItemToAccount(userId, itemId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void addItemToNonExistAccountTest() {
        int userId = 1;
        int itemId = 3;
        Item newItem = new Item(itemId,"Hat", 50);

        when(accountService.getAccountWithItemsById(userId)).thenReturn(null);
        when(itemService.getItemById(itemId)).thenReturn(newItem);

        ResponseEntity<List<Item>> response = accountController.addItemToAccount(userId, itemId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
