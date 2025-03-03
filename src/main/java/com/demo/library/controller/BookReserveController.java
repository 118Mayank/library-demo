package com.demo.library.controller;

import com.demo.library.dto.ReservationDTO;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.SearchDTO;
import com.demo.library.enums.ReservationStatus;
import com.demo.library.service.BookReserveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reserve")
@Api(value = "Crud operation for reservation", description = "APIs for Crud operation on reservation")
public class BookReserveController {

    @Autowired
    BookReserveService bookReserveService;

    @ApiOperation(value = "Register new reservation", notes = "API to add new book reservation against user.")
    @PostMapping("/create")
    public ResponseDTO createReservation(@RequestBody ReservationDTO reservationDTO, HttpServletRequest request) {
        return bookReserveService.createReservation(reservationDTO, request);
    }

    @ApiOperation(value = "Get reservation by reservation ID", notes = "API to get book reservation by ID.")
    @GetMapping("/get/{id}")
    public ResponseDTO getReservationById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return bookReserveService.getReservationById(id, request);
    }

    @ApiOperation(value = "Get all reservation", notes = "API to get all non deleted reservation.")
    @GetMapping("/getAll")
    public ResponseDTO getAllReservationById(HttpServletRequest request) {
        return bookReserveService.getAllReservation(request);
    }

    @ApiOperation(value = "Update status of reservation", notes = "API to update reservation by ID.")
    @PutMapping("/updateStatus")
    public ResponseDTO getAllReservationById(@RequestParam(name = "reservationId") long reservationId, @RequestParam(name = "reservationStatus") ReservationStatus reservationStatus, HttpServletRequest request) {
        return bookReserveService.updateReservationStatus(reservationId, reservationStatus, request);
    }

    @ApiOperation(value = "Delete by reservation ID", notes = "API to soft delete reservation by ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteById(@PathVariable(value = "id") long id, HttpServletRequest request) {
        return bookReserveService.deleteById(id, request);
    }

    @ApiOperation(value = "Search reservation", notes = "API to search reservation by registration date or due date.")
    @PostMapping("/filter")
    public ResponseDTO filterReservation(@RequestBody SearchDTO searchDTO, HttpServletRequest request) {
        return bookReserveService.filterReservation(searchDTO, request);
    }

    @ApiOperation(value = "Search reservation", notes = "API to return/submit book by ISBN code.")
    @PutMapping("/submit")
    public ResponseDTO CompleteReservation(@RequestParam(name = "isbnCode") String isbnCode, HttpServletRequest request) {
        return bookReserveService.getIssueStatus(isbnCode, request);
    }

    @ApiOperation(value = "Reservation history in XML", notes = "API to get reservation history by ISBN code. To get data in List pass [type='list'], by default data type is XML  ")
    @GetMapping("/getReservationHistoryByIsbn")
    public String getHistoryByIsbn(@RequestParam(name = "isbnCode") String isbnCode, @RequestParam(name = "type", required = false) String type) {
        return bookReserveService.getHistoryByIsbn(isbnCode, type);
    }

}
