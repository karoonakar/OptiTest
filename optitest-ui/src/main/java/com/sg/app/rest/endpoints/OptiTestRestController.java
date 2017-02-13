package com.sg.app.rest.endpoints;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sg.app.entities.ProcessRequest;
import com.sg.app.repositories.EmailSubscriberRepository;
import com.sg.app.rest.model.ProcessRequesResponseDTO;
import com.sg.app.rest.model.ProcessRequestDTO;
import com.sg.app.rest.model.PropertyDTO;
import com.sg.app.services.CodchnConfigService;
import com.sg.app.services.ProcessRequestService;
import com.sg.app.services.UserService;

/**
 * @author Karoonakar
 *
 */
@RestController
@RequestMapping(value = "/api")
public class OptiTestRestController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(OptiTestRestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private EmailSubscriberRepository emailSubscriberRepository;

	@Autowired
	private ProcessRequestService processRequestService;

	@Autowired
	private CodchnConfigService codchnConfigService;

	@RequestMapping(value = "/processrequest", method = RequestMethod.GET)
	public ProcessRequesResponseDTO findProcessRequest(ProcessRequestDTO request) {
		Page<ProcessRequest> pageData = processRequestService
				.findProcessRequest(request);
		ProcessRequesResponseDTO processRequesResponse = new ProcessRequesResponseDTO(
				pageData);
		return processRequesResponse;
	}

	@RequestMapping(value = "/getAllCodechn", method = RequestMethod.GET)
	public List<String> loadCodchnConfig() {
		return codchnConfigService.findAllCodchn();
	}

	@RequestMapping(value = "/getPropertyByCodchn/{codchn}", method = RequestMethod.GET)
	public List<PropertyDTO> loadPropertyByCodchn(
			@PathVariable(value = "codchn") String codchn) {
		return codchnConfigService.findByCodchn(codchn);
	}

	@RequestMapping(value = "/createProcessRequest", method = RequestMethod.GET)
	public String createProcessRequest(ProcessRequestDTO request) {
		String res = processRequestService.createProcessRequest(request);
		LOGGER.info("Request Created =>" + res);
		return res;
	}

	@RequestMapping(value = "/updateProcessRequestStatus", method = RequestMethod.GET)
	public String updateProcessRequesStatust(ProcessRequestDTO request) {
		processRequestService.updateProcessRequesStatust(request);
		LOGGER.info("Request updated =>" + request.getRequestId());
		return "Request Completed";
	}
}
