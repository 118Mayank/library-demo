package com.demo.library.controller;

import com.demo.library.dto.ResponseDTO;
import com.demo.library.dto.UserDTO;
import com.demo.library.service.UserServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Api(value = "Crud operation for user", description = "APIs for Crud operation on users")
public class UserController {

    @Autowired
    UserServices userServices;

    @ApiOperation(value = "Register new user", notes = "API to create user. Mobile number should be unique.")
    @PostMapping("/create")
    public ResponseDTO createUser(@RequestBody UserDTO userDTO, HttpServletRequest request){
        return userServices.createUser(userDTO,request);
    }

    @ApiOperation(value = "Get user by user ID", notes = "API to get user by ID.")
    @GetMapping("/get/{id}")
    public ResponseDTO getUserById(@PathVariable(required = true, value="id")long id, HttpServletRequest request){
        return userServices.getUserById(id, request);
    }

    @ApiOperation(value = "Get all users", notes = "API to get all users")
    @GetMapping("/getAll")
    public ResponseDTO getAllUsers(HttpServletRequest request){
        return userServices.getAllUserById(request);
    }

}
