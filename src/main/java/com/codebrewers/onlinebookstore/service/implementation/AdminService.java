package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.exception.AdminServiceException;
import com.codebrewers.onlinebookstore.dto.BookDTO;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IBookStoreService {

    @Autowired
    private IBookStoreRepository bookStoreRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public String addBook(BookDTO bookDTO) {
        BookDetails bookDetails = modelMapper.map(bookDTO,BookDetails.class);
        Optional<BookDetails> byIsbn = bookStoreRepository.findByIsbn(bookDTO.isbn);

        if(byIsbn.isPresent()){
            throw  new AdminServiceException("ISBN Number is Already Present");
        }
        Optional<BookDetails> byBookNameAndAuthorName = bookStoreRepository.findByBookNameAndAuthorName(bookDTO.bookName, bookDTO.authorName);
        if(byBookNameAndAuthorName.isPresent()) {
            throw new AdminServiceException("Book Already Present with " + bookDTO.bookName + " and " + bookDTO.authorName);
        }
           bookStoreRepository.save(bookDetails);
        return "Book Added Successfully";
    }

    @Override
    public List<BookDetails> allBooks() {
        return null;
    }
}
