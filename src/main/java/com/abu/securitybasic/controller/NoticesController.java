package com.abu.securitybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Notices controller.
 */
@RestController
@RequestMapping(value = "api/v1")
public class NoticesController {

	/**
	 * Gets notices.
	 *
	 * @param input the input
	 * @return the notices
	 */
	@GetMapping("/notices")
	public String getNotices(String input) {
		return "Here are the notices details from the DB";
	}

}
