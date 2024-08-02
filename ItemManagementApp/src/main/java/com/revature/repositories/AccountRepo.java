package com.revature.repositories;

import com.revature.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepo extends JpaRepository<Account, Integer> {

    Account findAccountByUsername(String username);

    //    @Query(value = "SELECT a.user_id, a.username, a.password, a.type, i.name, i.item_id, i.quantity " +
//                       "FROM ((accounts AS a " +
//               "LEFT JOIN accounts_items AS ai " +
//               "ON a.user_id = ai.user_id) " +
//               "LEFT JOIN items AS i " +
//               "ON ai.item_id=i.item_id) " +
//               "WHERE a.user_id = ?1", nativeQuery = true)
//    @Query("SELECT a, i FROM Account a LEFT JOIN a.items i WHERE a.id = :id")
//    Account findAccountWithItemsById(int id);
}
