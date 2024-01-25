package com.example.mainfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private Long customerId;
    private Long roomId;
    private Date startDate;
    private Date endDate;
    private String status;
}
