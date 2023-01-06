package com.sensei.views;

import com.sensei.dto.HistoryDto;
import com.sensei.service.TransactionHistoryService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("history")
public class HistoryView extends VerticalLayout {

    private TransactionHistoryService historyService = TransactionHistoryService.getInstance();
    private Grid<HistoryDto> grid = new Grid<>(HistoryDto.class);
    private NativeButton walletButton = new NativeButton("Back to wallet");

    public HistoryView() {
        grid.setColumns("id", "transactionType", "cryptocurrency", "quantity", "price", "value", "userId", "transactionTime");
        grid.setSizeFull();
        add(grid, walletButton);
        setSizeFull();
        refresh();
        walletButton.addClickListener(e ->
                walletButton.getUI().ifPresent(ui -> ui.navigate(WalletView.class)));
    }

    private void refresh() {
        grid.setItems(historyService.getHistory());
    }
}
