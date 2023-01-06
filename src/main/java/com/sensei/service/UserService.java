package com.sensei.service;

import com.sensei.dto.UserDto;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private static final String URL = "http://localhost:8080/v1/user";
    private final RestTemplate restTemplate;
    private static UserService userService;
    private List<String> usernames;

    private UserService() {
        restTemplate = new RestTemplate();
        usernames = new ArrayList<>();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public List<String> getUsernames() {
        setUsernames();
        return usernames;
    }

    public void setUsernames() {
        String[] users = restTemplate.getForObject(URL + "/usernames", String[].class);
        this.usernames = Arrays.asList(users);
    }

    public void addUser(UserDto userDto) {
        restTemplate.postForObject(URL, userDto, UserDto.class);
    }
    public void singIn() {
    }
}