package com.sensei.service;

import com.sensei.config.AuthConfig;
import com.sensei.dto.AuthDto;
import com.sensei.dto.CashHistoryDto;
import com.sensei.views.LoginForm;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class CashHistoryService {
    private static CashHistoryService service;
    public static final String URL_HISTORY = "http://localhost:8080/v1/history/cash/";
    private final RestTemplate restTemplate;
    private WalletService walletService;
    private List<CashHistoryDto> cashHistory;
    private AuthConfig authConfig;
    private AuthDto authDto;

    private CashHistoryService() {
        restTemplate = new RestTemplate();
        authDto = LoginForm.getAuthDto();
        authConfig = AuthConfig.getInstance();
        walletService = WalletService.getInstance();
    }

    public static CashHistoryService getInstance() {
        return service != null ? service : new CashHistoryService();
    }

    public List<CashHistoryDto> getCashHistory() {
        setCashHistory();
        return cashHistory;
    }

    public void setCashHistory() {
        Long userId = walletService.getWalletContent(authDto).getUserId();
        String url = URL_HISTORY + userId;
        HttpHeaders headers = authConfig.getRequestAuthenticate(authDto);
        WalletService.getInstance().getWalletContent(authDto).getUserId();
        HttpEntity<Object> request = new HttpEntity<>(headers);
        HttpEntity<CashHistoryDto[]> response =
                restTemplate.exchange(url, HttpMethod.GET, request, CashHistoryDto[].class);
        this.cashHistory = Arrays.asList(response.getBody());
    }
}
