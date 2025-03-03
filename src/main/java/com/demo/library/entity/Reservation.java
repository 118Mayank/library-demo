package com.demo.library.entity;

import com.demo.library.enums.ReservationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails userId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookDetails bookId;
    private ReservationStatus reservationStatus;
    private LocalDateTime registrationDate;
    private LocalDateTime dueDate;
    private String preferredContactMethod;
    private String userNotes;
    private boolean deleted;
    private LocalDateTime deletedTime;

    public Reservation(UserDetails userId, BookDetails bookId, ReservationStatus reservationStatus, LocalDateTime registrationDate, LocalDateTime dueDate, String preferredContactMethod, String userNotes, boolean deleted) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationStatus = reservationStatus;
        this.registrationDate = registrationDate;
        this.dueDate = dueDate;
        this.preferredContactMethod = preferredContactMethod;
        this.userNotes = userNotes;
        this.deleted = deleted;
    }
}
