package com.demo.library.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private LocalDateTime registeredDate;
    private String address;
    private String mobile;
    private Date dob;

    public UserDetails(String userName, LocalDateTime registeredDate, String address, String mobile, Date dob) {
        this.userName = userName;
        this.registeredDate = registeredDate;
        this.address = address;
        this.mobile = mobile;
        this.dob = dob;
    }
}
