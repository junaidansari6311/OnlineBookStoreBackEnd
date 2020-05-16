package com.codebrewers.onlinebookstore.dto;

import java.util.List;

public class SearchAndFilterResponseDTO {
        public List bookDetails;
        public int size;

        public SearchAndFilterResponseDTO(List bookDetails, int size) {
            this.bookDetails = bookDetails;
            this.size = size;
        }
}
