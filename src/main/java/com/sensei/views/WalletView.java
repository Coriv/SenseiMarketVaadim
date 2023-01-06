package com.sensei.views;

import com.sensei.service.WalletService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("wallet")
public class WalletView extends HorizontalLayout {

    private WalletService walletService = WalletService.getInstance();
    private CashWalletView cashWalletView = new CashWalletView(this);
    private WalletCryptoView walletCryptoView = new WalletCryptoView(this);
    private NativeButton goToHistory = new NativeButton("Go to transaction history");
    private NativeButton goToTrades = new NativeButton("Go to trades");
    private Button withdrawMoneyButton = new Button("Withdraw money");
    private Button depositMoneyButton = new Button("Deposit");
    private WithdrawView withdrawView = new WithdrawView(this);
    private DepositView depositView = new DepositView(this);

    public WalletView() {
        HorizontalLayout buttons = new HorizontalLayout(goToTrades, goToHistory, withdrawMoneyButton, depositMoneyButton);
        VerticalLayout wallets = new VerticalLayout( walletCryptoView, cashWalletView, withdrawView, depositView, buttons);
        withdrawView.setVisible(false);
        depositView.setVisible(false);
        add(wallets);
        setSizeFull();
        goToHistory.addClickListener( e ->
                goToHistory.getUI().ifPresent(ui -> ui.navigate(HistoryView.class)));
        goToTrades.addClickListener(e ->
                goToTrades.getUI().ifPresent(ui -> ui.navigate(TradeView.class)));
        withdrawMoneyButton.addClickListener(e -> withdrawView.setVisible(true));
        depositMoneyButton.addClickListener(e -> depositView.setVisible(true));
    }

    public void refresh() {
        cashWalletView.refresh();
        walletCryptoView.refresh();
    }
}
