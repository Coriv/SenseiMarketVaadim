package com.sensei.service;

import com.sensei.config.AuthConfig;
import com.sensei.dto.AuthDto;
import com.sensei.dto.WalletCryptoDto;
import com.sensei.dto.WalletDto;
import com.sensei.views.LoginForm;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WalletCryptoService {

    private RestTemplate restTemplate;
    private static WalletCryptoService walletCryptoService;

    public static final String URL_WALLET_CRYPTO = "http://localhost:8080/v1/wallet/";
    private List<WalletCryptoDto> cryptosList;
    private final AuthConfig authConfig = AuthConfig.getInstance();

    private WalletCryptoService() {
        this.restTemplate = new RestTemplate();
        cryptosList = new ArrayList<>();
    }

    public static WalletCryptoService getInstance() {
        return walletCryptoService != null ? walletCryptoService : new WalletCryptoService();
    }

    public List<WalletCryptoDto> getCryptosList() {
        setCryptosList();
        return cryptosList;
    }

    public void setCryptosList() {
        AuthDto authDto = LoginForm.getAuthDto();
        WalletDto walletDto = WalletService.getInstance().getWalletContent(authDto);
        String url = URL_WALLET_CRYPTO + walletDto.getId() + "?symbols=ALL";
        HttpHeaders headers = authConfig.getRequestAuthenticate(authDto);
        HttpEntity<WalletCryptoDto> request = new HttpEntity<>(headers);
        ResponseEntity<WalletCryptoDto[]> response =
                restTemplate.exchange(url, HttpMethod.GET, request, WalletCryptoDto[].class);
        WalletCryptoDto[] array = response.getBody();
        this.cryptosList = Arrays.asList(array);
    }
}
