package com.sensei.views;

import com.sensei.dto.CryptoPriceDto;
import com.sensei.service.CryptoPriceService;
import com.sensei.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    private UserService userService = UserService.getInstance();
    private CryptoPriceService cryptoPrice = CryptoPriceService.getInstance();
    private TextField filter = new TextField();
    private Grid<CryptoPriceDto> grid = new Grid<>(CryptoPriceDto.class);
    private LoginForm loginForm = new LoginForm(this);
    private UserRegisterForm registerForm = new UserRegisterForm(this);
    private HorizontalLayout mainContent;

    public MainView() {

        filter.setPlaceholder("Filter by SYMBOL");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        grid.setColumns("symbol", "bidPrice", "askPrice", "change24h", "volume", "time");
        VerticalLayout tableAndRegistryForm = new VerticalLayout(grid, registerForm);
        mainContent = new HorizontalLayout(tableAndRegistryForm, loginForm);
        registerForm.setVisible(false);
        mainContent.setSizeFull();
        grid.setSizeFull();
        add(filter, mainContent);
        setSizeFull();
        refresh();
    }

    private void update() {
        grid.setItems(cryptoPrice.findBySymbol(filter.getValue()));
    }

    public void refresh() {
        grid.setItems(cryptoPrice.getPrices());
    }

    public void showSingUpForm() {
        registerForm.setVisible(true);
    }

}
