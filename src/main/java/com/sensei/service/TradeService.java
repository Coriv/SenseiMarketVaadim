package com.sensei.service;

import com.sensei.config.AuthConfig;
import com.sensei.dto.AuthDto;
import com.sensei.dto.DealDto;
import com.sensei.dto.TradeDto;
import com.sensei.dto.WalletDto;
import com.sensei.views.LoginForm;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class TradeService {
    private final RestTemplate restTemplate;
    private static final String URL_TRADE = "http://localhost:8080/v1/trade";
    private static TradeService tradeService;
    private List<TradeDto> trades;
    private TradeDto currentTrade;
    private AuthDto authDto;
    private HttpHeaders headers;
    private WalletDto walletDto;

    private TradeService() {
        this.restTemplate = new RestTemplate();
        trades = new ArrayList<>();
        authDto = LoginForm.getAuthDto();
        walletDto = WalletService.getInstance().getWalletContent(authDto);
        headers = AuthConfig.getInstance().getRequestAuthenticate(authDto);
    }

    public static TradeService getInstance() {
        if (tradeService == null) {
            tradeService = new TradeService();
        }
        return tradeService;
    }

    public List<TradeDto> getOtherTrades() {
        setTrades();
        return trades
                .stream()
                .filter(trade -> trade.getWalletId() != walletDto.getId())
                .collect(Collectors.toList());
    }

    public List<TradeDto> getMyTrades() {
        setTrades();
        return trades
                .stream()
                .filter(trade -> trade.getWalletId() == walletDto.getId())
                .collect(Collectors.toList());
    }
    public void setTrades() {
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<TradeDto[]> response =
                restTemplate.exchange(URL_TRADE, HttpMethod.GET, request, TradeDto[].class);
        this.trades = Arrays.asList(response.getBody());
    }

    public void createTrade(Binder<TradeDto> binder) {
        TradeDto tradeDto = binder.getBean();
        HashMap<String, Object> body = new HashMap<>();
        body.put("transactionType", tradeDto.getTransactionType());
        body.put("cryptoSymbol", tradeDto.getCryptoSymbol());
        body.put("quantity", tradeDto.getQuantity());
        body.put("price", tradeDto.getPrice());
        body.put("walletId", walletDto.getId());
        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        restTemplate.exchange(URL_TRADE, HttpMethod.POST, request, TradeDto.class);
    }
    public void deleteTrade() {
        if(currentTrade.getWalletId() == walletDto.getId()) {
            String url = URL_TRADE + "/" + currentTrade.getId() + "/delete";
            HttpEntity<TradeDto> request = new HttpEntity<>(headers);
            restTemplate.exchange(url, HttpMethod.DELETE, request, TradeDto.class);
        }
        currentTrade = null;
    }
    public void setCurrentTradeDto(TradeDto tradeDto) {
        this.currentTrade = tradeDto;
    }

    public TradeDto getCurrentTrade() {
        return currentTrade;
    }

    public WalletDto getWalletDto() {
        return walletDto;
    }

    public void makeDeal(Binder<DealDto> binder) {
        DealDto dealDto = binder.getBean();
        String url = URL_TRADE + "/" + walletDto.getUserId() + "/" + currentTrade.getId() +
              "?quantity=" + dealDto.getQuantity();
        HttpEntity request = new HttpEntity(headers);
        restTemplate.exchange(url, HttpMethod.PUT, request, DealDto.class);
        currentTrade = null;
    }
}
