package com.sensei.views;

import com.sensei.dto.UserDto;
import com.sensei.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import lombok.extern.java.Log;

public class UserRegisterForm extends FormLayout {
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");
    private TextField PESEL = new TextField("PESEL");
    private TextField idCard = new TextField("ID Card");
    private TextField email = new TextField("Email");
    private Checkbox notification = new Checkbox("Subscribe information about new cryptocurrency");
    private Binder<UserDto> binder = new Binder<>(UserDto.class);
    private Button send = new Button("Send");
    private Button back = new Button("Back");
    private UserService userService = UserService.getInstance();
    private MainView mainView;

    public UserRegisterForm(MainView mainView) {
        HorizontalLayout buttons = new HorizontalLayout(send, back);
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, username, password, PESEL, idCard, email, notification, buttons);
        binder.forField(firstName)
                .withValidator(name -> name.length() > 1, "First name must contain at least two characters")
                .bind(UserDto::getFirstName, UserDto::setFirstName);
        binder.forField(lastName)
                .withValidator(lName -> lName.length() > 1, "Last name must contain at least two characters")
                .bind(UserDto::getLastName, UserDto::setLastName);
        binder.forField(username)
                .withValidator(uname -> uname.length() > 2, "Username must contain at least three characters")
                .withValidator(username ->  !userService.getUsernames().contains(username), "User with given username already exist, please try something else")
                .bind(UserDto::getUsername, UserDto::setUsername);
        binder.forField(password)
                .withValidator(pass -> pass.length() > 5, "Password must contain at 6 characters")
                .bind(UserDto::getPassword, UserDto::setPassword);
        binder.forField(PESEL)
                .withValidator(PESEL -> PESEL.length() == 11, "Pesel must contain exactly 11  characters")
                .withConverter(Long::valueOf, String::valueOf, "Please enter a number")
                .bind(UserDto::getPESEL, UserDto::setPESEL);
        binder.forField(email)
                .withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(UserDto::getEmail, UserDto::setEmail);
        binder.bindInstanceFields(this);
        binder.setBean(new UserDto());
        send.addClickListener(e -> save());
        this.mainView = mainView;
    }
    private void save() {
        UserDto userDto = binder.getBean();
        userService.addUser(userDto);
    }
}
