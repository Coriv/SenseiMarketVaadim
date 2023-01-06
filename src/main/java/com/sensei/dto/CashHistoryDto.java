package com.sensei.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CashHistoryDto {
    private Long id;
    private Long userId;
    private OperationType type;
    private BigDecimal quantityUSD;
    private BigDecimal quantityPLN;
    private String toAccount;
    private LocalDateTime time;
}
