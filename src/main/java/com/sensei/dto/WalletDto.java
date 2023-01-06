package com.sensei.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletDto {
    private Long id;
    private Long userId;
    private boolean active;
    private List<Long> walletsCryptoList;
    private List<Long> trades;
    private Long cashWalletId;
}
