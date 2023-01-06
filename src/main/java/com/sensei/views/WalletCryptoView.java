package com.sensei.views;

import com.sensei.dto.WalletCryptoDto;
import com.sensei.service.WalletCryptoService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WalletCryptoView extends VerticalLayout {

    private WalletCryptoService wCryptoService = WalletCryptoService.getInstance();
    private WalletView walletView;
    private Grid<WalletCryptoDto> grid = new Grid<>(WalletCryptoDto.class);

    public WalletCryptoView(WalletView walletView) {
        grid.setColumns("symbol", "quantity");
        grid.setSizeFull();
        add(grid);
        setSizeFull();
        if (LoginForm.getAuthDto() != null) {
        refresh();
        }
        this.walletView = walletView;
    }

    public void refresh() {
        grid.setItems(wCryptoService.getCryptosList());
    }
}
