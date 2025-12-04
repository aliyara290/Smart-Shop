package com.aliyara.smartshop.dto.request;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CheckPaymentRequestDTO {
    private String checkNumber;
    private String issuerName;
    private LocalDate dueDate;
}
