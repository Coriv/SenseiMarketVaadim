package com.sensei.service;

import com.sensei.config.AuthConfig;
import com.sensei.dto.AuthDto;
import com.sensei.dto.CashWalletDto;
import com.sensei.dto.WalletDto;
import com.sensei.views.LoginForm;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CashWalletService {

    private RestTemplate restTemplate;
    private CashWalletDto cashWalletDto;
    private static CashWalletService cashWalletService;
    private AuthConfig authConfig;
    private WalletService walletService;
    private AuthDto authDto;
    private static final String URL_CASH_WALLET = "http://localhost:8080/v1/wallet/cash/";

    private CashWalletService() {
        restTemplate = new RestTemplate();
        authConfig = AuthConfig.getInstance();
        walletService = WalletService.getInstance();
        authDto = LoginForm.getAuthDto();
    }

    public static CashWalletService getInstance() {
        if(cashWalletService == null) {
            cashWalletService = new CashWalletService();
        }
        return cashWalletService;
    }

    public CashWalletDto getCashWalletDto() {
        setCashWalletDto();
        return cashWalletDto;
    }

    public void setCashWalletDto() {
        var cashWalletId = walletService.getWalletContent(authDto).getCashWalletId();
        HttpHeaders header = authConfig.getRequestAuthenticate(authDto);
        HttpEntity<Object> request = new HttpEntity<>(header);
        String url = URL_CASH_WALLET + cashWalletId;
        ResponseEntity<CashWalletDto> response = restTemplate.exchange(url, HttpMethod.GET, request, CashWalletDto.class);
        var cashWalletDto = response.getBody();
        this.cashWalletDto = cashWalletDto;
    }

    public void withdraw(String value, BigDecimal value1) {
        var cashWalletId = walletService.getWalletContent(authDto).getCashWalletId();
        Map<String, Object> body = new HashMap<>();
        body.put("accountNumber", value);
        body.put("quantity", value1);
        String url = URL_CASH_WALLET + "/withdraw/" + cashWalletId;
        HttpHeaders header = authConfig.getRequestAuthenticate(authDto);
        HttpEntity<Object> request = new HttpEntity<>(body, header);
        restTemplate.exchange(url, HttpMethod.PUT, request, CashWalletDto.class);
    }

    public void deposit(BigDecimal valueOf) {
        var cashWalletId = walletService.getWalletContent(authDto).getCashWalletId();
        String url = URL_CASH_WALLET + "/deposit/" + cashWalletId + "?quantity=" + valueOf;
        HttpHeaders header = authConfig.getRequestAuthenticate(authDto);
        HttpEntity<Object> request = new HttpEntity<>(header);
        restTemplate.exchange(url, HttpMethod.PUT, request, CashWalletDto.class);
    }
}
