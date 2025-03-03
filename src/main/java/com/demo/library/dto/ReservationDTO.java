package com.demo.library.dto;

import com.demo.library.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;
    private Long userId;
    private Long bookId;
    private ReservationStatus reservationStatus;
    private LocalDateTime registrationDate;
    private LocalDateTime dueDate;
    private String preferredContactMethod;
    private String userNotes;

    public ReservationDTO(Long userId, Long bookId, ReservationStatus reservationStatus, String preferredContactMethod, String userNotes) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationStatus = reservationStatus;
        this.preferredContactMethod = preferredContactMethod;
        this.userNotes = userNotes;
    }

    public ReservationDTO(Long userId, Long bookId, ReservationStatus reservationStatus, String preferredContactMethod, String userNotes, LocalDateTime registrationDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationStatus = reservationStatus;
        this.preferredContactMethod = preferredContactMethod;
        this.userNotes = userNotes;
        this.registrationDate = registrationDate;
    }

    public boolean isValid() {
        boolean isvalid = false;
        if (this.userId != null && this.bookId != null)
            isvalid = true;
        return isvalid;
    }
}
