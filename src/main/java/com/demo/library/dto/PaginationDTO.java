package com.demo.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaginationDTO {
    private int pageSize;
    private int pageNumber;
    private int sortBy;
}
