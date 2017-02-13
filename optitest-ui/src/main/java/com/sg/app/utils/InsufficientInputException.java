package com.sg.app.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author Karoonakar
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Insufficient input parameters")
public class InsufficientInputException extends Exception {

	private static final long serialVersionUID = 1L;

}
