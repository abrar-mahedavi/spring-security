package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Balance controller.
 */
@RestController
@RequestMapping(value = "api/v1")
public class BalanceController {

	/**
	 * Gets balance details.
	 *
	 * @param input the input
	 * @return the balance details
	 */
	@GetMapping("/my-balance")
    public String getBalanceDetails(String input) {
        return "Here are the balance details from the DB";
    }

}
