package com.sg.app.services;

/**
 * @author Karoonakar
 *
 */

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sg.app.entities.OptiTestProcess;
import com.sg.app.entities.ProcessRequest;
import com.sg.app.repositories.CodchnConfigRepository;
import com.sg.app.repositories.ProcessRequestRepository;
import com.sg.app.rest.model.ProcessRequestDTO;
import com.sg.app.utils.DateUtil;

@Service
@Transactional
public class ProcessRequestService {

	@Autowired
	private ProcessRequestRepository processRequestRepository;

	@Autowired
	private CodchnConfigRepository codchnConfigRepository;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private JavaMailSender javaMailService;

	public Page<ProcessRequest> findProcessRequest(
			ProcessRequestDTO processRequest) {

		Sort sort = new Sort(Direction.DESC, "createdOn");
		if (processRequest.getPageNo() < 0) {
			processRequest.setPageNo(0);
		}
		if (processRequest.getPageSize() < 1) {
			processRequest.setPageSize(5);
		}
		Pageable pageable = new PageRequest(processRequest.getPageNo(),
				processRequest.getPageSize(), sort);
		Page<ProcessRequest> pageData = processRequestRepository
				.findAll(pageable);

		return pageData;
	}

	public List<ProcessRequest> findProcessRequest() {
		return processRequestRepository.findAll();
	}

	public ProcessRequest findProcessRequestById(int requestId) {
		return processRequestRepository.findOne(requestId);
	}

	public ProcessRequest createProcessRequest(ProcessRequest processRequest) {
		return processRequestRepository.save(processRequest);
	}

	public void deleteProcessRequest(Integer requestId) {
		processRequestRepository.delete(requestId);
	}

	public void updateProcessRequesStatust(ProcessRequestDTO request) {
		if (request.getRequestId() != null && request.getStatus() != null
				&& !request.getStatus().isEmpty()) {

			processRequestRepository.updateStatus(request.getRequestId(),
					request.getStatus());

			if ("Completed".equalsIgnoreCase(request.getStatus())) {

				ProcessRequest processRequestFromDb = processRequestRepository
						.findById(request.getRequestId());
				try {
					MimeMessage message = javaMailService.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message,
							true);
					helper.setSubject("Request Status for OptiTest request id :"
							+ processRequestFromDb.getId());
					helper.setTo(processRequestFromDb.getUserEmail());
					helper.setText("Hello!! Your request is successfull for "
							+ processRequestFromDb.getRequestTitle()
							+ " and Generated samples, can be downloaded from :"
							+ request.getDescription());
					javaMailService.send(message);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String createProcessRequest(ProcessRequestDTO request) {

		String batchDate = DateUtil.getHtmlToString(request.getBatchDate());

		ProcessRequest processRequest = new ProcessRequest();
		processRequest.setRequestTitle(request.getDescription());
		processRequest.setStatus("InProcess");
		processRequest.setCreatedOn(new Date());
		processRequest.setUpdatedOn(new Date());
		processRequest.setUserEmail(request.getEmail());
		processRequest.setBatchDate(batchDate);

		request.setBatchDate(batchDate);

		Set<OptiTestProcess> processSet = new HashSet<OptiTestProcess>();
		String propertyList[] = request.getSelectedPropertyList().split(",");

		for (String property : propertyList) {
			OptiTestProcess optiTestProcess = new OptiTestProcess(
					request.getCodchn(), property.trim());
			optiTestProcess.setProcessRequest(processRequest);
			processSet.add(optiTestProcess);
		}
		processRequest.setOptiTestProcess(processSet);

		ProcessRequest persistedRequest = processRequestRepository
				.save(processRequest);
		processRequestRepository.flush();

		Gson gson = new Gson();
		request.setRequestId(persistedRequest.getId());
		final String jsonString = gson.toJson(request);

		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(jsonString);
			}
		};

		jmsTemplate.send("OptiTestRequestQueue", messageCreator);

		return jsonString;
	}

}
