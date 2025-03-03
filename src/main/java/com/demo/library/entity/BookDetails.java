package com.demo.library.entity;

import com.demo.library.constants.ConstantValues;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_details")
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdDate;

    // ISBN  10 - 13 digits
//    @Column(length = ConstantValues.ISBNSIZE)
    private String isbnCode;

    private boolean available;
    private int publicationYear;

    public BookDetails(String title, String author, LocalDateTime createdDate, String isbnCode, boolean available, int publicationYear) {
        this.title = title;
        this.author = author;
        this.createdDate = createdDate;
        this.isbnCode = isbnCode;
        this.available = available;
        this.publicationYear = publicationYear;
    }
}
