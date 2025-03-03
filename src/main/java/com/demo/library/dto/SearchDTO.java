package com.demo.library.dto;

import com.demo.library.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    //search in book
    private String title;
    private String author;
    private Integer publicationYear;
    private String isbn;

    //search in reservation
    private long bookId;
    private LocalDateTime registrationDate;
    private LocalDateTime dueDate;

    public SearchDTO(String title, String author, Integer publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

//    private ReservationStatus reservationStatus;

}
