package com.demo.library.service;

import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.UserDTO;
import com.demo.library.entity.UserDetails;
import com.demo.library.repository.UserDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Mock
    private UserDetailsRepo userDetailsRepo;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserServices userServices;

    private UserDTO validUserDTO;
    private UserDTO invalidUserDTO;

    @BeforeEach
    public void setUp() throws ParseException {
        validUserDTO = new UserDTO("Ran prasad", "1234 raj colony", "1234567890", formatter.parse("1990-01-01"));
        invalidUserDTO = new UserDTO("", "", "", formatter.parse("1990-01-01")); // Invalid DTO
    }

    @Test
    public void testCreateUser_ValidUser() {
        // Mock behavior
        UserDetails savedUser = new UserDetails(validUserDTO.getUserName(), LocalDateTime.now(), validUserDTO.getAddress(), validUserDTO.getMobile(), validUserDTO.getDob());
        Mockito.when(userDetailsRepo.findByMobile(validUserDTO.getMobile())).thenReturn(Optional.empty());
        Mockito.when(userDetailsRepo.save(Mockito.any(UserDetails.class))).thenReturn(savedUser);
        Mockito.when(request.getRequestURI()).thenReturn("/createUser");
        ResponseDTO responseDTO = userServices.createUser(validUserDTO, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testCreateUser_InvalidUser() {
        // Call the method with an invalid userDTO
        ResponseDTO responseDTO = userServices.createUser(invalidUserDTO, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    @Test
    public void testCreateUser_UserExists() {
        //user already exists
        UserDetails existingUser = new UserDetails(validUserDTO.getUserName(), LocalDateTime.now(), validUserDTO.getAddress(), validUserDTO.getMobile(), validUserDTO.getDob());
        Mockito.when(userDetailsRepo.findByMobile(validUserDTO.getMobile())).thenReturn(Optional.of(existingUser));
        Mockito.when(request.getRequestURI()).thenReturn("/createUser");
        ResponseDTO responseDTO = userServices.createUser(validUserDTO, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetUserById_UserFound() throws ParseException {
        //user found
        UserDetails userDetails = new UserDetails("John Doe", LocalDateTime.now(), "1234 Street", "1234567890", formatter.parse("1990-01-01"));
        Mockito.when(userDetailsRepo.findById(1L)).thenReturn(Optional.of(userDetails));
        Mockito.when(request.getRequestURI()).thenReturn("/getUserById");
        ResponseDTO responseDTO = userServices.getUserById(1L, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        //user not found
        Mockito.when(userDetailsRepo.findById(999L)).thenReturn(Optional.empty());
        Mockito.when(request.getRequestURI()).thenReturn("/getUserById");
        ResponseDTO responseDTO = userServices.getUserById(999L, request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetUserById_Exception() {
        //exception occurs during fetch
        Mockito.when(userDetailsRepo.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Database error"));
        Mockito.when(request.getRequestURI()).thenReturn("/getUserById");
        ResponseDTO responseDTO = userServices.getUserById(1L, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }

    @Test
    public void testGetAllUserById_Success() throws ParseException {
        //successful retrieval of users
        Mockito.when(userDetailsRepo.findAll()).thenReturn(Collections.singletonList(new UserDetails("John Doe", LocalDateTime.now(), "1234 Street", "1234567890", formatter.parse("1990-01-01"))));
        Mockito.when(request.getRequestURI()).thenReturn("/getAllUsers");
        ResponseDTO responseDTO = userServices.getAllUserById(request);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
    }

    @Test
    public void testGetAllUserById_Exception() {
        //exception occurs during fetch
        Mockito.when(userDetailsRepo.findAll()).thenThrow(new RuntimeException("Database error"));
        Mockito.when(request.getRequestURI()).thenReturn("/getAllUsers");
        ResponseDTO responseDTO = userServices.getAllUserById(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());
    }
}
