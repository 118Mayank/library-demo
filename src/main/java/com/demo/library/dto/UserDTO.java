package com.demo.library.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long userId;
    private String userName;
    private String address;
    private String mobile;
    private Date dob;

    public UserDTO(String userName, String address, String mobile, Date dob) {
        this.userName = userName;
        this.address = address;
        this.mobile = mobile;
        this.dob = dob;
    }

    public boolean isValid() {
        boolean isvalid = false;
        if (this.userName != null && !this.userName.trim().isEmpty() && this.address != null && !this.address.trim().isEmpty() && this.mobile != null && this.mobile.length()==10 && this.dob != null)
            isvalid = true;
        return isvalid;
    }
}
