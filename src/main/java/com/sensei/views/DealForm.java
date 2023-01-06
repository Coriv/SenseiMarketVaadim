package com.sensei.views;

import com.sensei.dto.DealDto;
import com.sensei.service.TradeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class DealForm extends FormLayout {
    private Binder<DealDto> binder = new Binder<>(DealDto.class);
    private TextField quantity = new TextField("Quantity");
    private Button makeDeal = new Button("Make deal");
    private Button cancel = new Button("Cancel");
    private TradeView tradeView;
    private TradeService tradeService = TradeService.getInstance();

    public DealForm(TradeView tradeView) {
        HorizontalLayout buttons = new HorizontalLayout(makeDeal, cancel);
        add(quantity, buttons);
        setVisible(false);
        binder.bindInstanceFields(this);
        binder.setBean(new DealDto());
        this.tradeView = tradeView;
        makeDeal.addClickListener(e -> {
            tradeService.makeDeal(binder);
            tradeView.getOtherTrades();
            setVisible(false);
        });
        cancel.addClickListener(e -> setVisible(false));
    }
}
