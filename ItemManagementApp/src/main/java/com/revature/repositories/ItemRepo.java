package com.revature.repositories;

import com.revature.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Integer> {

    Item findItemByName(String name);


//    @Query(value = "SELECT i.* FROM accounts AS a " +
//               "JOIN accounts_items AS ai " +
//               "ON a.user_id = ai.user_id " +
//               "JOIN items AS i " +
//               "ON ai.item_id=i.item_id " +
//               "WHERE a.user_id = ?1", nativeQuery = true)
    @Query("SELECT i FROM Item i JOIN FETCH i.accounts a WHERE a.id = :userId")
    List<Item> findItemsByUserId(int userId);
}
