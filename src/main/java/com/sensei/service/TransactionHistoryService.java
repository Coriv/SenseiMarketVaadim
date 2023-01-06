package com.sensei.service;

import com.sensei.config.AuthConfig;
import com.sensei.dto.AuthDto;
import com.sensei.dto.HistoryDto;
import com.sensei.dto.WalletDto;
import com.sensei.views.LoginForm;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class TransactionHistoryService {
    private static TransactionHistoryService historyService;
    private final RestTemplate restTemplate;
    private List<HistoryDto> history;
    public static final String URL_HISTORY = "http://localhost:8080/v1/history/";
    private TransactionHistoryService() {
        restTemplate = new RestTemplate();
    }

    public static TransactionHistoryService getInstance() {
        return historyService != null ? historyService : new TransactionHistoryService();
    }

    public List<HistoryDto> getHistory() {
        setHistory();
        return history;
    }

    public void setHistory() {
        AuthDto authDto = LoginForm.getAuthDto();
        WalletDto walletDto = WalletService.getInstance().getWalletContent(authDto);
        HttpHeaders headers = AuthConfig.getInstance().getRequestAuthenticate(authDto);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        String url = URL_HISTORY + walletDto.getUserId();
        HttpEntity<HistoryDto[]> historyDto =
                restTemplate.exchange(url, HttpMethod.GET, request, HistoryDto[].class);
        this.history = Arrays.asList(historyDto.getBody());
    }
}
