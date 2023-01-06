package com.sensei.views;

import com.sensei.dto.CashWalletDto;
import com.sensei.service.CashWalletService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CashWalletView extends VerticalLayout {
    private CashWalletService cashService = CashWalletService.getInstance();
    private WalletView walletView;
    private Grid<CashWalletDto> grid = new Grid<>(CashWalletDto.class);
    public CashWalletView(WalletView walletView) {
        grid.setColumns("currency", "quantity");
        grid.setSizeFull();
        add(grid);
        if (LoginForm.getAuthDto() != null) {
            refresh();
        }
        setSizeFull();
        this.walletView = walletView;
    }

    public void refresh() {
        grid.setItems(cashService.getCashWalletDto());
    }
}
