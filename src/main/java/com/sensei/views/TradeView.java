package com.sensei.views;

import com.sensei.dto.TradeDto;
import com.sensei.service.TradeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("trade")
public class TradeView extends VerticalLayout {

    private final TradeService tradeService = TradeService.getInstance();
    private Grid<TradeDto> grid = new Grid<>(TradeDto.class);
    private NativeButton goToWallet = new NativeButton("Wallet");
    private Button showMyTrades = new Button("Show my trades");
    private Button showOtherTrades = new Button("Show other trades");
    private Button createTrade = new Button("Create trade");
    private CreateTradeForm createTradeForm = new CreateTradeForm(this);
    private DealForm dealForm = new DealForm(this);
    private Button delete = new Button("Delete");

    public TradeView() {
        grid.setColumns("id", "transactionType", "cryptoSymbol", "quantity", "price");
        grid.setSizeFull();
        HorizontalLayout buttons = new HorizontalLayout(goToWallet, showMyTrades, showOtherTrades,
                createTrade, delete);
        showOtherTrades.setVisible(false);
        delete.setVisible(false);
        goToWallet.addClickListener(e ->
                goToWallet.getUI().ifPresent(ui -> ui.navigate(WalletView.class)));
        showMyTrades.addClickListener(e -> {
            getMyTrades();
            showOtherTrades.setVisible(true);
            showMyTrades.setVisible(false);
        });
        showOtherTrades.addClickListener(e -> {
            getOtherTrades();
            showOtherTrades.setVisible(false);
            showMyTrades.setVisible(true);
            delete.setVisible(false);
        });
        add(grid, createTradeForm, dealForm, buttons);
        createTradeForm.setVisible(false);
        getOtherTrades();
        setSizeFull();
        createTrade.addClickListener(e -> createTradeForm.setVisible(true));
        delete.addClickListener(e -> {
            tradeService.deleteTrade();
            getMyTrades();
        });
        grid.asSingleSelect().addValueChangeListener(e -> {
            tradeService.setCurrentTradeDto(grid.asSingleSelect().getValue());
            System.out.println(tradeService.getCurrentTrade() + " w dobrym miejscu");
            if (tradeService.getCurrentTrade() != null) {
                if (tradeService.getCurrentTrade().getWalletId() != tradeService.getWalletDto().getId()) {
                    dealForm.setVisible(true);
                } else {
                    delete.setVisible(true);
                }
            } else {
                dealForm.setVisible(false);
                delete.setVisible(false);
            }

        });
    }
    public void getOtherTrades() {
        grid.setItems(tradeService.getOtherTrades());
    }

    public void getMyTrades() {
        grid.setItems(tradeService.getMyTrades());
    }
}
