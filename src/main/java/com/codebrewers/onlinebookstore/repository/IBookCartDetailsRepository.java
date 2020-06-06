package com.codebrewers.onlinebookstore.repository;

import com.codebrewers.onlinebookstore.model.BookCartDetails;
import com.codebrewers.onlinebookstore.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IBookCartDetailsRepository extends JpaRepository<BookCartDetails,Integer> {


    @Query(value = "select * from book_cart_details where cart_id = :cartId and order_status = false ", nativeQuery = true)
    List<BookCartDetails> fetchCartItems(@Param("cartId") Integer cartId);

    @Transactional
    @Modifying
    @Query(value = "update book_cart_details set order_status = true where cart_id = :cartId and order_status = false ", nativeQuery = true)
    int updateOrderPlacedStatus(@Param("cartId") Integer cartId);
}
