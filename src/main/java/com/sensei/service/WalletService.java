package com.sensei.service;

import com.sensei.config.AuthConfig;
import com.sensei.dto.AuthDto;
import com.sensei.dto.WalletDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class WalletService {
    private static WalletService walletService;
    private static final String URL_WALLET = "http://localhost:8080/v1/wallet/";
    private static final String URL_FIND_WALLET = "http://localhost:8080/v1/user/findWallet?username=";
    private RestTemplate restTemplate;
    private static WalletDto walletDto;
    private AuthConfig authConfig = AuthConfig.getInstance();

    private WalletService() {
        this.restTemplate = new RestTemplate();
    }

    public static WalletService getInstance() {
        if (walletService == null) {
            walletService = new WalletService();
        }
        return walletService;
    }

    public WalletDto getWalletContent(AuthDto authDto) {
        setWalletContent(authDto);
        return walletDto;
    }

    public void setWalletContent(AuthDto authDto) {
        URI url = buildURL(authDto);
        HttpHeaders headers = authConfig.getRequestAuthenticate(authDto);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<WalletDto> response = restTemplate.exchange(url, HttpMethod.GET, request, WalletDto.class);
        this.walletDto = response.getBody();
    }

    private URI buildURL(AuthDto authDto) {
        return UriComponentsBuilder.fromHttpUrl(URL_WALLET + authenticateAndGetWallet(authDto))
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }

    private Long authenticateAndGetWallet(AuthDto authDto) {
        String url = URL_FIND_WALLET + authDto.getUsername();
        HttpHeaders headers = authConfig.getRequestAuthenticate(authDto);
        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<Long> response = restTemplate.exchange(url, HttpMethod.GET, request, Long.class);
        var walletId = response.getBody();
        return walletId;
    }
}
