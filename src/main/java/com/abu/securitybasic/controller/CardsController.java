package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Cards controller.
 */
@RestController
@RequestMapping(value = "api/v1")
public class CardsController {

	/**
	 * Gets card details.
	 *
	 * @param input the input
	 * @return the card details
	 */
	@GetMapping("/my-cards")
	public String getCardDetails(String input) {
		return "Here are the card details from the DB";
	}

}
