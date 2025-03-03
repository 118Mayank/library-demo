package com.demo.library.service;

import com.demo.library.constants.ConstantMessage;
import com.demo.library.dto.BookDTO;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.SearchDTO;
import com.demo.library.entity.BookDetails;
import com.demo.library.repository.BookDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookDetailsService {

    @Autowired
    BookDetailsRepo bookDetailsRepo;

    private static final Logger bdLogger = LoggerFactory.getLogger(BookDetailsService.class);

    public ResponseDTO createBookDetail(BookDTO bookDTO, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        if (Objects.nonNull(bookDTO) && bookDTO.isValid()) {
            BookDetails bbookDetails = new BookDetails(bookDTO.getTitle(), bookDTO.getAuthor(), LocalDateTime.now(), bookDTO.getIsbnCode(), true, bookDTO.getPublicationYear());
            Optional<BookDetails> bookDetails = bookDetailsRepo.findByIsbnCode(bookDTO.getIsbnCode());
            if (!bookDetails.isPresent()) {
                BookDetails savedBook = bookDetailsRepo.save(bbookDetails);
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), savedBook, ConstantMessage.NewEntry + savedBook.getId(), request.getRequestURI());
                bdLogger.info("New book added- {}", savedBook);
            } else
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.BookExists, request.getRequestURI());
        }
        return responseDTO;
    }

    public ResponseDTO getBookById(long id, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            Optional<BookDetails> bookDetails = bookDetailsRepo.findById(id);
            if (bookDetails.isPresent())
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), bookDetails, ConstantMessage.AvailableData, request.getRequestURI());
            else
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.NotFound, request.getRequestURI());
        } catch (Exception e) {
            bdLogger.error("Error while fetch book with id {} error {}", id, e.getLocalizedMessage());
        }
        return responseDTO;
    }

    public ResponseDTO getAllBook(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), bookDetailsRepo.findAll(), ConstantMessage.AvailableData, request.getRequestURI());
        } catch (Exception e) {
            bdLogger.error("Error while fetch All book {}", e.getLocalizedMessage());
        }
        return responseDTO;
    }

    public ResponseDTO getBookByIsbn(String getBookByIsbn, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        Optional<BookDetails> bookDetails = bookDetailsRepo.findByIsbnCode(getBookByIsbn);
        if (bookDetails.isPresent()) {
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), bookDetails.get(), ConstantMessage.NewEntry + bookDetails.get().getId(), request.getRequestURI());
            bdLogger.info("New user generated- {}", bookDetails.get());
        } else
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.NotFound, request.getRequestURI());
        return responseDTO;
    }

    public ResponseDTO filterReservation(SearchDTO searchDTO, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            if (searchDTO.getIsbn() != null && !searchDTO.getIsbn().isEmpty()) {
                responseDTO = getBookByIsbn(searchDTO.getIsbn(), request);
            } else {
                List<BookDetails> bookDetails = bookDetailsRepo.findBooksByCriteriaNative(searchDTO.getTitle(), searchDTO.getAuthor(), searchDTO.getPublicationYear());
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), bookDetails, ConstantMessage.AvailableData, request.getRequestURI());
            }

        } catch (Exception e) {
            bdLogger.error("Error while fetch search {} result {}", searchDTO, e.getLocalizedMessage());
        }
        return responseDTO;
    }
}
