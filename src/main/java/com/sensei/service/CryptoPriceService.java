package com.sensei.service;

import com.sensei.dto.CryptoPriceDto;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CryptoPriceService {
    private final RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/v1/price";
    private static CryptoPriceService cryptoPriceService;
    private Set<CryptoPriceDto> prices;

    private CryptoPriceService() {
        this.restTemplate = new RestTemplate();
        prices = new HashSet<>();
    }

    public static CryptoPriceService getInstance() {
        return cryptoPriceService != null ? cryptoPriceService : new CryptoPriceService();
    }

    public Set<CryptoPriceDto> getPrices() {
        setPrices();
        return prices;
    }

    public void setPrices() {
        CryptoPriceDto[] cryptoPrices = restTemplate.getForObject(URL, CryptoPriceDto[].class);
        prices = Arrays.asList(cryptoPrices).stream()
                .collect(Collectors.toSet());
    }

    public Set<CryptoPriceDto> findBySymbol(String symbol) {
        return prices.stream()
                .filter(crypto -> crypto.getSymbol().contains(symbol))
                .collect(Collectors.toSet());
    }
}
