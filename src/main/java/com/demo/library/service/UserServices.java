package com.demo.library.service;

import com.demo.library.constants.ConstantMessage;
import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.UserDTO;
import com.demo.library.entity.UserDetails;
import com.demo.library.repository.UserDetailsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    UserDetailsRepo userDetailsRepo;

    private static final Logger usLogger = LoggerFactory.getLogger(UserServices.class);

    public ResponseDTO createUser(UserDTO userDTO, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        if (Objects.nonNull(userDTO) && userDTO.isValid()) {
            Optional<UserDetails> userByMobile = userDetailsRepo.findByMobile(userDTO.getMobile());
            if (!userByMobile.isPresent()) {
                UserDetails savedUser = userDetailsRepo.save(new UserDetails(userDTO.getUserName(), LocalDateTime.now(), userDTO.getAddress(), userDTO.getMobile(), userDTO.getDob()));
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), savedUser, ConstantMessage.NewEntry + savedUser.getId(), request.getRequestURI());
            } else
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.UserExists, request.getRequestURI());
        }
        return responseDTO;
    }

    public ResponseDTO getUserById(long id, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            Optional<UserDetails> userDetails = userDetailsRepo.findById(id);
            if (userDetails.isPresent())
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), userDetails, ConstantMessage.AvailableData, request.getRequestURI());
            else
                responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), ConstantMessage.NotFound, request.getRequestURI());
        } catch (Exception e) {
            usLogger.error("Error while fetch user with id {} error {}", id, e.getLocalizedMessage());
        }
        return responseDTO;
    }

    public ResponseDTO getAllUserById(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ConstantMessage.InvalidData, Collections.emptyList(), request.getRequestURI());
        try {
            responseDTO = new ResponseDTO(HttpStatus.OK, LocalDateTime.now(), userDetailsRepo.findAll(), ConstantMessage.AvailableData, request.getRequestURI());
        } catch (Exception e) {
            usLogger.error("Error while fetch All user {}", e.getLocalizedMessage());
        }
        return responseDTO;
    }
}
