package com.demo.library.repository;

import com.demo.library.entity.BookDetails;
import com.demo.library.entity.Reservation;
import com.demo.library.entity.UserDetails;
import com.demo.library.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT * FROM reservation WHERE user_id = :userId AND book_id = :bookId AND reservation_status = :reservationStatus AND deleted = false", nativeQuery = true)
    Optional<Reservation> findReservationsByUserIdAndBookIdAndStatus(@Param("userId") UserDetails userId, @Param("bookId") BookDetails bookId, @Param("reservationStatus") ReservationStatus reservationStatus);

//    @Query(value = "SELECT * FROM reservation WHERE registration_date BETWEEN :startRegDate AND :endRegDate OR (r.dueDate BETWEEN :startDueDate AND :endDueDate)", nativeQuery = true)
//    List<Reservation> findReservationsBetweenDates(@Param("startRegDate") String startRegDate, @Param("endRegDate") String endRegDate,@Param("startDueDate") String startDueDate, @Param("endDueDate") String endDueDate);

    @Query(value = "SELECT * FROM reservation WHERE (registration_date BETWEEN :startRegDate AND :endRegDate) OR (due_date BETWEEN :startDueDate AND :endDueDate)", nativeQuery = true)
    List<Reservation> findReservationsBetweenDates(@Param("startRegDate") String startRegDate, @Param("endRegDate") String endRegDate,@Param("startDueDate") String startDueDate, @Param("endDueDate") String endDueDate);

    Optional<Reservation> findByBookIdAndReservationStatusAndDeletedFalse(BookDetails bookDetails, ReservationStatus reservationStatus);

    Optional<Reservation> findByIdAndDeletedFalse(Long id);

    List<Reservation> findByDeletedFalse();

    List<Reservation> findAllByBookIdAndDeletedFalse(BookDetails bookDetails);
}
