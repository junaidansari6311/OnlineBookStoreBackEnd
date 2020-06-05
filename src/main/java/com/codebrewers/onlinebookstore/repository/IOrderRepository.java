package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.OrderDetails;
import com.codebrewers.onlinebookstore.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderDetails, Integer> {


    Optional<OrderDetails> findByOrderId(Integer orderId);

    List<OrderDetails> findOrderDetailsByUser(UserDetails userDetails);
}