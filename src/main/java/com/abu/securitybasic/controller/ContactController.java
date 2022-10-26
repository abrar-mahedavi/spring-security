package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Contact controller.
 */
@RestController
@RequestMapping(value = "api/v1")
public class ContactController {

	/**
	 * Save contact inquiry details string.
	 *
	 * @param input the input
	 * @return the string
	 */
	@GetMapping("/contact")
	public String saveContactInquiryDetails(String input) {
		return "Inquiry details are saved to the DB";
	}

}
