package com.revature.repositories;

import com.revature.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Integer> {

    Item findItemByName(String name);
}
