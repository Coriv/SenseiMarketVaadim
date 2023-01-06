package com.sensei.views;

import com.sensei.dto.AuthDto;
import com.sensei.service.UserService;
import com.sensei.service.WalletService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class LoginForm extends FormLayout {

    private MainView mainView;
    private UserService userService = UserService.getInstance();
    private WalletService walletService = WalletService.getInstance();
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private Binder<AuthDto> binder = new Binder<>(AuthDto.class);
    private static AuthDto authDto;
    private Button singIn = new Button("Sing in");
    private Button singUp = new Button("Sing up");
    public LoginForm(MainView mainView) {
        HorizontalLayout buttons = new HorizontalLayout(singIn, singUp);
        add(username, password, buttons);
            binder.forField(username)
                    .withValidator(username -> userService.getUsernames().contains(username), "Given username doest not exist. " +
                            "Please fill correct username or Sing Up")
                    .bind(AuthDto::getUsername, AuthDto::setUsername);
        binder.forField(password)
                .bind(AuthDto::getPassword, AuthDto::setPassword);
        binder.setBean(new AuthDto());
        singIn.addClickListener(e -> {
            singInAction();
            if(authDto.getUsername() != null && authDto.getPassword() != null) {
                singIn.getUI().ifPresent(ui -> ui.navigate(WalletView.class));
            }
        });
        singUp.addClickListener(e -> singUpAction());
        this.mainView = mainView;
    }

    private void singInAction() {
        authDto = binder.getBean();
        walletService.getWalletContent(authDto);

    }
    private void singUpAction() {
        mainView.showSingUpForm();
    }

    public static AuthDto getAuthDto() {
        return authDto;
    }
}
