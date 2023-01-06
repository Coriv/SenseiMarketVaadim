package com.sensei.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeDto {
    private Long id;
    private TransactionType transactionType;
    private String cryptoSymbol;
    private BigDecimal quantity;
    private BigDecimal price;
    private Long walletId;
}
