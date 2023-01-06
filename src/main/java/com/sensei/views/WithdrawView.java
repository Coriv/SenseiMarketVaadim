package com.sensei.views;

import com.sensei.service.CashWalletService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
public class WithdrawView extends FormLayout {
    private final CashWalletService cashWalletService = CashWalletService.getInstance();
    private WalletView walletView;
    private TextField accountNumbre = new TextField("Account number");
    private NumberField quantity = new NumberField("Quantity");
    private Button send = new Button("Send");
    private Button cancel = new Button("Cancel");

    public WithdrawView(WalletView walletView) {
        HorizontalLayout buttons = new HorizontalLayout(send, cancel);
        add(accountNumbre, quantity, buttons);
        setSizeFull();
        setVisible(false);
        send.addClickListener(e -> {
            cashWalletService.withdraw(accountNumbre.getValue(), BigDecimal.valueOf(quantity.getValue()));
            setVisible(false);
            walletView.refresh();
        });
        cancel.addClickListener(e -> setVisible(false));
        this.walletView = walletView;
    }
}
