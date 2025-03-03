package com.demo.library.service;

import com.demo.library.config.ReservationXmlConverter;
import com.demo.library.constants.ConstantMessage;
import com.demo.library.constants.ConstantValues;
import com.demo.library.dto.ReservationDTO;
import com.demo.library.dto.ResponseDTO;

import com.demo.library.dto.SearchDTO;
import com.demo.library.entity.BookDetails;
import com.demo.library.entity.Reservation;
import com.demo.library.entity.UserDetails;
import com.demo.library.enums.ReservationStatus;
import com.demo.library.repository.BookDetailsRepo;
import com.demo.library.repository.ReservationRepo;
import com.demo.library.repository.UserDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookReserveService {

    @Autowired
    ReservationRepo reservationRepo;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    BookDetailsRepo bookDetailsRepo;

    private static final Logger brLogger = LoggerFactory.getLogger(BookDetailsService.class);


    public ResponseDTO createReservation(ReservationDTO reservationDTO, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        if (Objects.nonNull(reservationDTO) && reservationDTO.isValid()) {
            Optional<UserDetails> userDetails = userDetailsRepo.findById(reservationDTO.getUserId());
            Optional<BookDetails> bookDetails = bookDetailsRepo.findById(reservationDTO.getBookId());
            if (userDetails.isPresent() && bookDetails.isPresent()) {
                Optional<Reservation> reservation = reservationRepo.findReservationsByUserIdAndBookIdAndStatus(userDetails.get(), bookDetails.get(), reservationDTO.getReservationStatus());
                if (reservation.isPresent()) {
                    responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.ReservationExists, request.getRequestURI());
                } else if (checkBookAvailability(reservationDTO.getBookId())) {
                    Reservation savedreservation = reservationRepo.save(new Reservation(userDetails.get(), bookDetails.get(), reservationDTO.getReservationStatus(), LocalDateTime.now(), LocalDateTime.now().plusDays(ConstantValues.RESERVEDAYS), reservationDTO.getPreferredContactMethod(), reservationDTO.getUserNotes(), false));
                    responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), savedreservation, ConstantMessage.NewEntry + savedreservation.getId(), request.getRequestURI());
                    brLogger.info("New reservation generated- {}", savedreservation);
                } else
                    responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.NA, request.getRequestURI());
            } else
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.InvalidData, request.getRequestURI());

        }
        return responseDTO;
    }

    public ResponseDTO getReservationById(Long id, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            Optional<Reservation> reservation = reservationRepo.findByIdAndDeletedFalse(id);
            if (reservation.isPresent())
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), reservation, ConstantMessage.AvailableData, request.getRequestURI());
            else
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.NotFound, request.getRequestURI());
        } catch (Exception e) {
            brLogger.error("Error while fetch reservation with id {} error {}", id, e.getLocalizedMessage());
        }
        return responseDTO;
    }

    public ResponseDTO getAllReservation(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), reservationRepo.findByDeletedFalse(), ConstantMessage.AvailableData, request.getRequestURI());
        } catch (Exception e) {
            brLogger.error("Error while fetch All reservation {}", e.getLocalizedMessage());
        }
        return responseDTO;
    }

    public boolean checkBookAvailability(long bookId) {
        Optional<BookDetails> bookDetails = bookDetailsRepo.findById(bookId);
        if (bookDetails.isPresent()) {
            Optional<Reservation> reservationsList = reservationRepo.findByBookIdAndReservationStatusAndDeletedFalse(bookDetails.get(), ReservationStatus.ISSUED);
            return !reservationsList.isPresent();
        }
        return false;
    }

    public ResponseDTO updateReservationStatus(long reservationId, ReservationStatus reservationStatus, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        Optional<Reservation> optReservations = reservationRepo.findByIdAndDeletedFalse(reservationId);
        if (optReservations.isPresent()) {
            try {
                if (reservationStatus != ReservationStatus.ISSUED || checkBookAvailability(optReservations.get().getBookId().getId())) {
                    optReservations.get().setReservationStatus(reservationStatus);
                    Reservation reservation = reservationRepo.save(optReservations.get());
                    responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), reservation, ConstantMessage.AvailableData, request.getRequestURI());
                }
            } catch (NullPointerException ne) {
                brLogger.error("Error while updating status {}", ne.getLocalizedMessage());
            }
        }
        return responseDTO;
    }

    public ResponseDTO deleteById(long reservationId, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            Optional<Reservation> optReservations = reservationRepo.findByIdAndDeletedFalse(reservationId);
            if (optReservations.isPresent()) {
                optReservations.get().setDeleted(true);
                optReservations.get().setDeletedTime(LocalDateTime.now());
                reservationRepo.save(optReservations.get());
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), Collections.emptyList(), ConstantMessage.Done, request.getRequestURI());
            }
        } catch (Exception e) {
            brLogger.error("Error while deleting reservation {}", e.getLocalizedMessage());
        }
        return responseDTO;
    }

    public ResponseDTO filterReservation(SearchDTO searchDTO, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            List<Reservation> reservations = reservationRepo.findReservationsBetweenDates(searchDTO.getRegistrationDate().toLocalDate().atTime(LocalTime.MIN).toString(), searchDTO.getRegistrationDate().toLocalDate().atTime(LocalTime.MAX).toString(), searchDTO.getDueDate().toLocalDate().atTime(LocalTime.MIN).toString(), searchDTO.getDueDate().toLocalDate().atTime(LocalTime.MAX).toString());
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), reservations, ConstantMessage.AvailableData, request.getRequestURI());
        } catch (Exception e) {
            brLogger.error("Error while fetch search {} result {}", searchDTO, e.getLocalizedMessage());
        }

        return responseDTO;
    }

    public ResponseDTO getIssueStatus(String isbnCode, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());

        Optional<BookDetails> bookDetails = bookDetailsRepo.findByIsbnCode(isbnCode);
        if (bookDetails.isPresent()) {
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.NotFound, Collections.emptyList(), request.getRequestURI());
            Optional<Reservation> reservation = reservationRepo.findByBookIdAndReservationStatusAndDeletedFalse(bookDetails.get(), ReservationStatus.ISSUED);
            if (reservation.isPresent() && Objects.nonNull(reservation.get().getDueDate())) {
                double fine = calculateFine(reservation.get().getDueDate(), LocalDateTime.now());
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), reservation.get(), ConstantMessage.TotalFine + fine, request.getRequestURI());
            }
        }
        return responseDTO;
    }

    public double calculateFine(LocalDateTime dueDate, LocalDateTime returnDate) {
        double fine = 0.0;
        if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate))
            fine = 0.0;
        else {
            long daysDelayed = Duration.between(dueDate, returnDate).toDays();

            if (daysDelayed > 0 && daysDelayed <= ConstantValues.Fine05InMonth)
                fine = daysDelayed * ConstantValues.FineFor2Week; // $1 per day for delay between 15 and 30 days
            else if (daysDelayed > ConstantValues.Fine05InMonth)
                fine = (daysDelayed - ConstantValues.Fine05InMonth) * ConstantValues.FineAfterMonth + (ConstantValues.Fine05InMonth); // $1 per day for first 30 days and $0.5 per day for more than 30 days
        }
        return fine;
    }


    public String getHistoryByIsbn(String isbnCode, String type) {
        Optional<BookDetails> bookDetails = bookDetailsRepo.findByIsbnCode(isbnCode);
        String historyData = "";
        try {
            if (bookDetails.isPresent()) {
                List<Reservation> reservationsHistory = reservationRepo.findAllByBookIdAndDeletedFalse(bookDetails.get());
                if (type.equals("list"))
                    historyData = reservationsHistory.toString();
                else
                    historyData = ReservationXmlConverter.convertListToXml(reservationsHistory);
            }
        } catch (Exception e) {
            brLogger.error("Error while creating history xml");
        }
        return historyData;
    }

}

