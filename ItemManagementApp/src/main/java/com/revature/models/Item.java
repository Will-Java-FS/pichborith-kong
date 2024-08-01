package com.revature.models;

import jakarta.persistence.*;

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
