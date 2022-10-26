package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Account controller.
 */
@RestController
@RequestMapping(value = "api/v1")
public class AccountController {

    /**
     * Get account details string.
     *
     * @return the string
     */
    @GetMapping("/my-account")
    public String getAccountDetails(){
        return "My account details.";
    }
}
