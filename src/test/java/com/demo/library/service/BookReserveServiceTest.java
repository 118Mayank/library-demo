package com.demo.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import com.demo.library.dto.ReservationDTO;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.entity.BookDetails;
import com.demo.library.entity.Reservation;
import com.demo.library.entity.UserDetails;
import com.demo.library.enums.ReservationStatus;
import com.demo.library.repository.BookDetailsRepo;
import com.demo.library.repository.ReservationRepo;
import com.demo.library.repository.UserDetailsRepo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


@ExtendWith(MockitoExtension.class)
public class BookReserveServiceTest {

    @InjectMocks
    private BookReserveService bookReserveService;

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private UserDetailsRepo userDetailsRepo;

    @Mock
    private BookDetailsRepo bookDetailsRepo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Reservation reservation;

    @Mock
    private UserDetails userDetails;

    @Mock
    private BookDetails bookDetails;

    //Create Reservation - Success Scenario
    @Test
    public void testCreateReservation_Success() {
        ReservationDTO reservationDTO = new ReservationDTO(1L, 1L, ReservationStatus.RESERVED, "test contact", "test notes", LocalDateTime.now());

        when(userDetailsRepo.findById(anyLong())).thenReturn(Optional.of(userDetails));
        when(bookDetailsRepo.findById(anyLong())).thenReturn(Optional.of(bookDetails));
        when(reservationRepo.findReservationsByUserIdAndBookIdAndStatus(any(), any(), any())).thenReturn(Optional.empty());
        when(bookReserveService.checkBookAvailability(anyLong())).thenReturn(true);
        when(reservationRepo.findByBookIdAndReservationStatusAndDeletedFalse(any(), any())).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);

        ResponseDTO responseDTO = bookReserveService.createReservation(reservationDTO, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    //Create Reservation - Invalid ReservationDTO
    @Test
    public void testCreateReservation_InvalidDTO() {
        ReservationDTO reservationDTO = new ReservationDTO(null, null, null, null, null, null);
        ResponseDTO responseDTO = bookReserveService.createReservation(reservationDTO, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    //Get Reservation by ID - Found
    @Test
    public void testGetReservationById_Found() {
        long reservationId = 1L;
        when(reservationRepo.findByIdAndDeletedFalse(reservationId)).thenReturn(Optional.of(reservation));
        ResponseDTO responseDTO = bookReserveService.getReservationById(reservationId, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
        assertNotNull(responseDTO.getData());
    }

    //Get Reservation by ID - Not Found
    @Test
    public void testGetReservationById_NotFound() {
        long reservationId = 1L;
        when(reservationRepo.findByIdAndDeletedFalse(reservationId)).thenReturn(Optional.empty());
        ResponseDTO responseDTO = bookReserveService.getReservationById(reservationId, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    //Get All Reservations
    @Test
    public void testGetAllReservations() {
        when(reservationRepo.findByDeletedFalse()).thenReturn(Collections.singletonList(reservation));
        ResponseDTO responseDTO = bookReserveService.getAllReservation(request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    //Check Book Availability - Available
    @Test
    public void testCheckBookAvailability_Available() {
        when(bookDetailsRepo.findById(anyLong())).thenReturn(Optional.of(bookDetails));
        when(reservationRepo.findByBookIdAndReservationStatusAndDeletedFalse(any(), any())).thenReturn(Optional.empty());
        boolean result = bookReserveService.checkBookAvailability(1L);
        assertTrue(result);
    }

    //Check Book Availability - Not Available
    @Test
    public void testCheckBookAvailability_NotAvailable() {
        when(bookDetailsRepo.findById(anyLong())).thenReturn(Optional.of(bookDetails));
        when(reservationRepo.findByBookIdAndReservationStatusAndDeletedFalse(any(), any())).thenReturn(Optional.of(reservation));
        boolean result = bookReserveService.checkBookAvailability(1L);
        assertFalse(result);
    }

    //Update Reservation Status - Success
    @Test
    public void testUpdateReservationStatus_Success() {
        long reservationId = 1L;
        when(reservationRepo.findByIdAndDeletedFalse(reservationId)).thenReturn(Optional.of(reservation));
        lenient().when(bookDetailsRepo.findById(anyLong())).thenReturn(Optional.of(new BookDetails()));
        lenient().when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);
        ResponseDTO responseDTO = bookReserveService.updateReservationStatus(reservationId, ReservationStatus.ISSUED, request);
    }

    //Update Reservation Status - Invalid Reservation
    @Test
    public void testUpdateReservationStatus_InvalidReservation() {
        long reservationId = 1L;
        when(reservationRepo.findByIdAndDeletedFalse(reservationId)).thenReturn(Optional.empty());
        ResponseDTO responseDTO = bookReserveService.updateReservationStatus(reservationId, ReservationStatus.ISSUED, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    //Delete Reservation - Success
    @Test
    public void testDeleteReservation_Success() {
        long reservationId = 1L;
        when(reservationRepo.findByIdAndDeletedFalse(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any(Reservation.class))).thenReturn(reservation);
        ResponseDTO responseDTO = bookReserveService.deleteById(reservationId, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    //Delete Reservation - Not Found
    @Test
    public void testDeleteReservation_NotFound() {
        long reservationId = 1L;
        when(reservationRepo.findByIdAndDeletedFalse(reservationId)).thenReturn(Optional.empty());
        ResponseDTO responseDTO = bookReserveService.deleteById(reservationId, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    //Get Issue Status - Found
    @Test
    public void testGetIssueStatus_Found() {
        String isbnCode = "978-3-16-148410-0";
        when(bookDetailsRepo.findByIsbnCode(isbnCode)).thenReturn(Optional.of(bookDetails));
        when(reservationRepo.findByBookIdAndReservationStatusAndDeletedFalse(any(), eq(ReservationStatus.ISSUED)))
                .thenReturn(Optional.of(reservation));

        ResponseDTO responseDTO = bookReserveService.getIssueStatus(isbnCode, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    //Get Issue Status - Not Found
    @Test
    public void testGetIssueStatus_NotFound() {
        String isbnCode = "978-3-16-148410-0";
        when(bookDetailsRepo.findByIsbnCode(isbnCode)).thenReturn(Optional.empty());
        ResponseDTO responseDTO = bookReserveService.getIssueStatus(isbnCode, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }
}
