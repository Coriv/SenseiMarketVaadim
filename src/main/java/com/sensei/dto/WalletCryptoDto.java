package com.sensei.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletCryptoDto {
    @JsonProperty("cryptocurrencySymbol")
    private String symbol;
    private BigDecimal quantity;
}
