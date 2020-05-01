package com.bridgelabz.onlinebookstore.Service.Implementation;

import com.bridgelabz.onlinebookstore.DTO.BookStoreDTO;
import com.bridgelabz.onlinebookstore.Repository.IBookStoreRepository;
import com.bridgelabz.onlinebookstore.Service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookStoreService implements IBookStoreService {

    @Autowired
    private IBookStoreRepository bookStoreRepository;

    @Override
    public String getAddedBooks(BookStoreDTO bookStoreDto) {
        bookStoreRepository.save(bookStoreDto);
        return "Books Added Successfully";
    }
}
