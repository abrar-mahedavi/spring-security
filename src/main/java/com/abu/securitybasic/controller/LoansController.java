package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Loans controller.
 */
@RestController
@RequestMapping(value = "api/v1")
public class LoansController {

	/**
	 * Gets loan details.
	 *
	 * @param input the input
	 * @return the loan details
	 */
	@GetMapping("/my-loans")
	public String getLoanDetails(String input) {
		return "Here are the loan details from the DB";
	}

}
