package com.aliyara.smartshop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TransferPaymentRequestDTO {
    private String bankName;
    private String referenceNumber;
    private String senderName;
}
