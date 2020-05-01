package com.bridgelabz.onlinebookstore.Repository;

import com.bridgelabz.onlinebookstore.DTO.BookStoreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookStoreRepository extends JpaRepository<BookStoreDTO,Integer> {

}
