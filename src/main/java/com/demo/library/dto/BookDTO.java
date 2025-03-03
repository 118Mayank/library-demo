package com.demo.library.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO{

    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdDate;
    private String isbnCode;
    private boolean available;
    private int publicationYear;


    public BookDTO(String title, String author, String isbnCode, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbnCode = isbnCode;
        this.publicationYear = publicationYear;
    }

    public boolean isValid() {
        boolean isvalid=false;
            if (this.title != null && !this.title.trim().isEmpty() && this.author != null && !this.author.trim().isEmpty() && this.isbnCode != null && !this.isbnCode.trim().isEmpty() && this.publicationYear >0) {
                isvalid= true;
            }
        return isvalid;
    }
}
