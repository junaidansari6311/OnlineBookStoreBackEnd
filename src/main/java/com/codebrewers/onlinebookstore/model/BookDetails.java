package com.codebrewers.onlinebookstore.model;
import com.codebrewers.onlinebookstore.dto.BookDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String bookName;
    public String authorName;
    public String description;
    public String imageUrl;
    public String isbn;
    public double bookPrice;
    public double quantity;
    public int publishingYear;

    public BookDetails(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.bookPrice = bookDTO.getBookPrice();
        this.isbn = bookDTO.getIsbn();
        this.quantity = bookDTO.getQuantity();
        this.description = bookDTO.getDescription();
        this.imageUrl = bookDTO.getImageUrl();
        this.publishingYear = bookDTO.getPublishingYear();
    }
}
