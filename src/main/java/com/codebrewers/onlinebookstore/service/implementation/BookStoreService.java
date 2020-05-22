package com.codebrewers.onlinebookstore.service.implementation;

import com.codebrewers.onlinebookstore.dto.SearchAndFilterResponseDTO;
import com.codebrewers.onlinebookstore.enums.BookStoreEnum;
import com.codebrewers.onlinebookstore.exception.BookStoreException;
import com.codebrewers.onlinebookstore.model.BookDetails;
import com.codebrewers.onlinebookstore.repository.IBookStoreRepository;
import com.codebrewers.onlinebookstore.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class BookStoreService implements IBookStoreService {

    @Value("${image.file.path}")
    private String imagePath;

    @Autowired
    private IBookStoreRepository bookStoreRepository;


    @Override
    public List<BookDetails> allBooks(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<BookDetails> bookList = bookStoreRepository.findAll(paging);
        if (!bookList.hasContent()) {
            throw new BookStoreException("No Books Available");
        }
        return bookList.getContent();
    }

    @Override
    public int getCount() {
        List<BookDetails> totalBooks = bookStoreRepository.findAll();
        return totalBooks.size();
    }

    @Override
    public SearchAndFilterResponseDTO findAllBooks(String searchText, int pageNo, BookStoreEnum selectedfield) {
        List<BookDetails> allBooks = null;
        if (searchText.equals("none")) {
            allBooks = bookStoreRepository.findAll();
        }
        if (!searchText.equals("none")) {
            allBooks = bookStoreRepository.findSortedBooks(searchText);
        }

        List<BookDetails> sortedData = selectedfield.getSortedData(allBooks);
        PagedListHolder page = new PagedListHolder(sortedData);
        page.setPageSize(8);
        page.setPage(pageNo);
        SearchAndFilterResponseDTO searchAndFilterResponseDTO = new SearchAndFilterResponseDTO(page.getPageList(), allBooks.size());
        return searchAndFilterResponseDTO;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            String fileBasePath = System.getProperty("user.dir")+ "\\src\\main\\resources\\Images\\";
            Path path = Paths.get(fileBasePath + fileName);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new BookStoreException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new BookStoreException("File not found " + fileName);
        }
    }

}
