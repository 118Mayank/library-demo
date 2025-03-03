package com.demo.library.service;

import com.demo.library.dto.BookDTO;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.SearchDTO;
import com.demo.library.entity.BookDetails;
import com.demo.library.repository.BookDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookDetailsServiceTest {
    @Mock
    private BookDetailsRepo bookDetailsRepo;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private BookDetailsService bookDetailsService;

    private BookDTO validBookDTO;
    private BookDTO invalidBookDTO;
    private SearchDTO searchDTO;

    @BeforeEach
    public void setUp() {
        validBookDTO = new BookDTO("Java Programming", "Johnny prasad", "978-3-16-148410-0", 2021);
        invalidBookDTO = new BookDTO("", "", "", 0); // Invalid DTO
        searchDTO = new SearchDTO("Java Programming", "James singh", 2021);
    }

    @Test
    public void testCreateBookDetail_ValidBook() {
        //book does not exist
        BookDetails savedBook = new BookDetails(validBookDTO.getTitle(), validBookDTO.getAuthor(), LocalDateTime.now(), validBookDTO.getIsbnCode(), true, validBookDTO.getPublicationYear());
        Mockito.when(bookDetailsRepo.findByIsbnCode(validBookDTO.getIsbnCode())).thenReturn(Optional.empty());
        Mockito.when(bookDetailsRepo.save(Mockito.any(BookDetails.class))).thenReturn(savedBook);
        Mockito.when(request.getRequestURI()).thenReturn("/createBook");
        ResponseDTO responseDTO = bookDetailsService.createBookDetail(validBookDTO, request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testCreateBookDetail_InvalidBook() {
        // Call the method with an invalid bookDTO
        ResponseDTO responseDTO = bookDetailsService.createBookDetail(invalidBookDTO, request);        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    @Test
    public void testCreateBookDetail_BookExists() {
        //book already exists
        BookDetails existingBook = new BookDetails(validBookDTO.getTitle(), validBookDTO.getAuthor(), LocalDateTime.now(), validBookDTO.getIsbnCode(), true, validBookDTO.getPublicationYear());
        Mockito.when(bookDetailsRepo.findByIsbnCode(validBookDTO.getIsbnCode())).thenReturn(Optional.of(existingBook));
        Mockito.when(request.getRequestURI()).thenReturn("/createBook");
        ResponseDTO responseDTO = bookDetailsService.createBookDetail(validBookDTO, request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetBookById_BookFound() {
        //book found
        BookDetails bookDetails = new BookDetails("Java Programming", "John Doe", LocalDateTime.now(), "978-3-16-148410-0", true, 2021);
        Mockito.when(bookDetailsRepo.findById(1L)).thenReturn(Optional.of(bookDetails));
        Mockito.when(request.getRequestURI()).thenReturn("/getBookById");
        ResponseDTO responseDTO = bookDetailsService.getBookById(1L, request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetBookById_BookNotFound() {
        //book not found
        Mockito.when(bookDetailsRepo.findById(999L)).thenReturn(Optional.empty());
        Mockito.when(request.getRequestURI()).thenReturn("/getBookById");
        ResponseDTO responseDTO = bookDetailsService.getBookById(999L, request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetBookById_Exception() {
        //exception occurs during fetch
        Mockito.when(bookDetailsRepo.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Database error"));
        Mockito.when(request.getRequestURI()).thenReturn("/getBookById");
        ResponseDTO responseDTO = bookDetailsService.getBookById(1L, request);        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    @Test
    public void testGetAllBook_Success() {
        //successful retrieval of all books
        Mockito.when(bookDetailsRepo.findAll()).thenReturn(Collections.singletonList(new BookDetails("Java Programming", "John Doe", LocalDateTime.now(), "978-3-16-148410-0", true, 2021)));
        Mockito.when(request.getRequestURI()).thenReturn("/getAllBooks");
        ResponseDTO responseDTO = bookDetailsService.getAllBook(request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetAllBook_Exception() {
        //exception occurs during fetch
        Mockito.when(bookDetailsRepo.findAll()).thenThrow(new RuntimeException("Database error"));
        Mockito.when(request.getRequestURI()).thenReturn("/getAllBooks");
        ResponseDTO responseDTO = bookDetailsService.getAllBook(request);        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    @Test
    public void testGetBookByIsbn_BookFound() {
        //book found by ISBN
        BookDetails bookDetails = new BookDetails("Java Programming", "Raj jain", LocalDateTime.now(), "978-3-16-148410-0", true, 2021);
        Mockito.when(bookDetailsRepo.findByIsbnCode("978-3-16-148410-0")).thenReturn(Optional.of(bookDetails));
        Mockito.when(request.getRequestURI()).thenReturn("/getBookByIsbn");
        ResponseDTO responseDTO = bookDetailsService.getBookByIsbn("978-3-16-148410-0", request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetBookByIsbn_BookNotFound() {
        //book not found by ISBN
        Mockito.when(bookDetailsRepo.findByIsbnCode("978-3-16-148410-0")).thenReturn(Optional.empty());
        Mockito.when(request.getRequestURI()).thenReturn("/getBookByIsbn");
        ResponseDTO responseDTO = bookDetailsService.getBookByIsbn("978-3-16-148410-0", request);        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testFilterReservation_WithIsbn() {
        // Removed the unnecessary stubbing for findByIsbnCode
        Mockito.when(request.getRequestURI()).thenReturn("/filterReservation");

        SearchDTO searchDTOWithIsbn = new SearchDTO("978-3-16-148410-0", null, 0);
        ResponseDTO responseDTO = bookDetailsService.filterReservation(searchDTOWithIsbn, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
//        assertTrue(responseDTO.getMessage().contains("NewEntry"));
    }


    @Test
    public void testFilterReservation_WithCriteria() {
        //search by title, author, and publication year
        Mockito.when(bookDetailsRepo.findBooksByCriteriaNative("Java Programming", "John singh", 2021)).thenReturn(Collections.singletonList(new BookDetails("Java Programming", "John Doe", LocalDateTime.now(), "978-3-16-148410-0", true, 2021)));
        Mockito.when(request.getRequestURI()).thenReturn("/filterReservation");

        ResponseDTO responseDTO = bookDetailsService.filterReservation(searchDTO, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testFilterReservation_Exception() {
        //exception occurs during filter search
        Mockito.when(bookDetailsRepo.findBooksByCriteriaNative(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
                .thenThrow(new RuntimeException("Database error"));
        Mockito.when(request.getRequestURI()).thenReturn("/filterReservation");

        ResponseDTO responseDTO = bookDetailsService.filterReservation(searchDTO, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }
}
