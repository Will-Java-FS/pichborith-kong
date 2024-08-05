package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "accounts_items",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    public Account() {}

    public Account( String username, String password) {
        this.password = password;
        this.username = username;
    }

    public Account(int id, String username, String password) {
        this(username, password);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;
        return getId() == account.getId()
                   && Objects.equals(getUsername(), account.getUsername())
                   && Objects.equals(getPassword(), account.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + Objects.hashCode(getUsername());
        result = 31 * result + Objects.hashCode(getPassword());
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                   "id= " + id +
                   ", username= '" + username + '\'' +
                   ", password= '" + password + '\'' +
                   '}';
    }
}
