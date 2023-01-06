package com.sensei.views;

import com.sensei.service.CashWalletService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;

import java.math.BigDecimal;

public class DepositView extends FormLayout {
    private CashWalletService cashWalletService = CashWalletService.getInstance();
    private NumberField quantity = new NumberField("Quantity PLN");
    private Button deposit = new Button("Deposit");
    private Button cancel = new Button("Cancel");
    private WalletView walletView;

    public DepositView(WalletView walletView) {
        HorizontalLayout buttons = new HorizontalLayout(deposit, cancel);
        add(quantity, buttons);
        setSizeFull();
        setVisible(false);
        deposit.addClickListener(e -> {
            cashWalletService.deposit(BigDecimal.valueOf(quantity.getValue()));
            walletView.refresh();
            setVisible(false);
        });
        cancel.addClickListener(e -> setVisible(false));
        this.walletView = walletView;
    }
}
