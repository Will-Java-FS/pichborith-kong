package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    private int quantity;

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//                           CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "items")
//    @JoinTable(name = "accounts_items",
//        joinColumns = @JoinColumn(name = "item_id"),
//        inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "items")
    @JsonIgnore
    private List<Account> accounts = new ArrayList<>();;

    public Item() {}

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Item(String name) {
        this(name, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Item{" +
                   "id= " + id +
                   ", name= '" + name + '\'' +
                   ", quantity= " + quantity +
                   '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;
        return getId() == item.getId()
                   && getQuantity() == item.getQuantity()
                   && Objects.equals(getName(), item.getName());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + Objects.hashCode(getName());
        result = 31 * result + getQuantity();
        return result;
    }
}
