package com.app.springsecurity.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/cards")
    public String getCards() {
        return "List of cards";
    }

}
