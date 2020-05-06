package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.Exception.AdminServiceException;
import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookStoreService implements IBookStoreService {

    @Autowired
    private IBookStoreRepository bookStoreRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BookDetails getAddedBooks(BookDTO bookDTO) {
//        BookDetails bookDetails = new BookDetails(bookDTO);
        BookDetails bookDetails = modelMapper.map(bookDTO,BookDetails.class);
        Optional<BookDetails> byIsbn = bookStoreRepository.findByIsbn(bookDTO.getIsbn());
        Optional<BookDetails> byBookNameAndAuthorName = bookStoreRepository.findByBookNameAndAuthorName(bookDTO.getBookName(), bookDTO.getAuthorName());
        if(byIsbn.isPresent()){
            throw  new AdminServiceException("ISBN Number is Already Present");
        }else if(byBookNameAndAuthorName.isPresent()) {
            throw new AdminServiceException("Book Already Present with " + bookDTO.getBookName() + " and " + bookDTO.getAuthorName());
        }
          return bookStoreRepository.save(bookDetails);
       // return "Book Added Successfully";
//        return null;
    }
}
