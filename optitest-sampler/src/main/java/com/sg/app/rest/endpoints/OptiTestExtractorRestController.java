package com.sg.app.rest.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.app.rest.model.ProcessRequestDTO;
/**
 * @author Karoonakar
 *
 */
@RestController
@RequestMapping(value="/extractapi")
public class OptiTestExtractorRestController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OptiTestExtractorRestController.class);
	
	public String createProcessRequest(ProcessRequestDTO request) {
		LOGGER.debug("TODO");
		return "";
	}
}
