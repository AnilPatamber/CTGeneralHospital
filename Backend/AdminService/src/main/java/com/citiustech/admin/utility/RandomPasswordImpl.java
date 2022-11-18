package com.citiustech.admin.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
public class RandomPasswordImpl implements RandomPassword {
	public String generateRandomPassword() {

		String generatedPassword = "";
		generatedPassword = RandomStringUtils.randomAlphabetic(1).toUpperCase();
		generatedPassword += RandomStringUtils.randomAlphabetic(1).toLowerCase();
		generatedPassword += RandomStringUtils.randomAlphanumeric(5);
		generatedPassword += RandomStringUtils.randomNumeric(1);
		generatedPassword += RandomStringUtils.randomAlphabetic(2).toLowerCase();

		return generatedPassword;

	}
}