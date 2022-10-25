package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Home controller.
 */
@RestController
public class HomeController {

    /**
     * Home string.
     *
     * @return the string
     */
    @GetMapping("/home")
    public String home() {
        return "Welcome me to home page";
    }
}
