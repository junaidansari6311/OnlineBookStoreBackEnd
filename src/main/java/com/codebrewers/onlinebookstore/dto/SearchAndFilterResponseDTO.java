package com.codebrewers.onlinebookstore.dto;

import com.codebrewers.onlinebookstore.model.BookDetails;
import lombok.Getter;

import java.util.List;
@Getter
public class SearchAndFilterResponseDTO {
        List bookDetails;
        int size;

        public SearchAndFilterResponseDTO(List bookDetails, int size) {
            this.bookDetails = bookDetails;
            this.size = size;
        }
}
