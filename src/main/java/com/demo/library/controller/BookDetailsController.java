package com.demo.library.controller;

import com.demo.library.dto.BookDTO;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.SearchDTO;
import com.demo.library.service.BookDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
@Api(value = "Crud operation for book", description = "APIs for Crud operation on books")
public class BookDetailsController {

    @Autowired
    BookDetailsService bookDetailsService;

    @ApiOperation(value = "Register new books", notes = "API to add new book. ISBN should be unique.")
    @PostMapping("/create")
    public ResponseDTO createUser(@RequestBody BookDTO bookDTO, HttpServletRequest request) {
        return bookDetailsService.createBookDetail(bookDTO, request);
    }

    @ApiOperation(value = "Get book by book ID", notes = "API to get book by ID.")
    @GetMapping("/get/{id}")
    public ResponseDTO getBookById(@PathVariable(required = true, value = "id") long id, HttpServletRequest request) {
        return bookDetailsService.getBookById(id, request);
    }

    @ApiOperation(value = "Get all books", notes = "API to get all books.")
    @GetMapping("/getAll")
    public ResponseDTO getAllBookById(HttpServletRequest request) {
        return bookDetailsService.getAllBook(request);
    }

    @ApiOperation(value = "Get book by ISBN", notes = "API to get books by ISBN.")
    @GetMapping("/getBookByIsbn")
    public ResponseDTO getBookByIsbn(@RequestParam(name="isbnCode",required = true)String getBookByIsbn, HttpServletRequest request) {
        return bookDetailsService.getBookByIsbn(getBookByIsbn, request);
    }

    @ApiOperation(value = "Search book", notes = "API to search books by title, auther, publicationYear or ISBN.")
    @PostMapping("/filter")
    public ResponseDTO filterReservation(@RequestBody SearchDTO searchDTO, HttpServletRequest request) {
        return bookDetailsService.filterReservation(searchDTO, request);
    }
}
