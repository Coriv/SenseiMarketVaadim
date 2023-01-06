package com.sensei.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryDto {
    private Long id;
    private TransactionType transactionType;
    private String cryptocurrency;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal value;
    private Long userId;
    private LocalDateTime transactionTime;
}
