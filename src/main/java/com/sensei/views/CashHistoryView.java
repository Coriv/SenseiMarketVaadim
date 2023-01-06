package com.sensei.views;

import com.sensei.dto.CashHistoryDto;
import com.sensei.service.CashHistoryService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("cashHistory")
public class CashHistoryView extends VerticalLayout {

    private NativeButton moveToWallet = new NativeButton("Wallet");
    private CashHistoryService cashHistoryService = CashHistoryService.getInstance();
    private Grid<CashHistoryDto> grid = new Grid<>(CashHistoryDto.class);

    public CashHistoryView() {
        grid.setColumns("id", "userId", "type", "quantityUSD", "quantityPLN", "toAccount", "time");
        grid.setSizeFull();
        add(grid, moveToWallet);
        setSizeFull();
        moveToWallet.addClickListener(e ->
                moveToWallet.getUI().ifPresent(ui -> ui.navigate(WalletView.class)));
        refresh();
    }

    public void refresh() {
        grid.setItems(cashHistoryService.getCashHistory());
    }
}
