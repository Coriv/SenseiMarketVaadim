package com.sensei.views;

import com.sensei.dto.TradeDto;
import com.sensei.dto.TransactionType;
import com.sensei.service.TradeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CreateTradeForm extends FormLayout {

    private TradeService tradeService = TradeService.getInstance();
    private Binder<TradeDto> binder = new Binder<>(TradeDto.class);
    private ComboBox<TransactionType> type = new ComboBox<>("Type");
    private TextField cryptoSymbol = new TextField("Cryptocurrency");
    private TextField quantity = new TextField("Quantity");
    private TextField price = new TextField("Price");
    private Button add = new Button("Add trade");
    private Button close = new Button("Close");
    private TradeView tradeView;

    public CreateTradeForm(TradeView tradeView) {
        HorizontalLayout buttons = new HorizontalLayout(add, close);
        type.setItems(TransactionType.values());
        add(type, cryptoSymbol, quantity, price, buttons);
        binder.forField(type)
                .bind(TradeDto::getTransactionType, TradeDto::setTransactionType);
        binder.forField(cryptoSymbol)
                .withValidator(symbol -> symbol.length() > 2, "Minimum 2 characters.")
                .bind(TradeDto::getCryptoSymbol, TradeDto::setCryptoSymbol);
        binder.bindInstanceFields(this);
        binder.setBean(new TradeDto());
        setSizeFull();
        setVisible(false);
        add.addClickListener(e -> {
            tradeService.createTrade(binder);
            setVisible(false);
            tradeView.getMyTrades();
        });
        close.addClickListener(e -> setVisible(false));
        this.tradeView = tradeView;
    }

    public void showFormAddTrade() {
        setVisible(true);
    }
}
