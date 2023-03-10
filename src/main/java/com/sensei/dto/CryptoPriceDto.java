package com.sensei.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoPriceDto {
    private String symbol;
    private String bidPrice;
    private String askPrice;
    @JsonProperty("priceChangePercent")
    private String change24h;
    private String volume;
    private LocalDateTime time;
}
