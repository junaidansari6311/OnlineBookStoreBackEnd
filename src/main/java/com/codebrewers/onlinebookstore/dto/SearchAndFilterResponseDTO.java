package com.codebrewers.onlinebookstore.dto;

import java.util.List;

public class SearchAndFilterResponseDTO {
    public List books;
    public int size;

    public SearchAndFilterResponseDTO(List books, int size) {
        this.books = books;
        this.size = size;
    }
}
