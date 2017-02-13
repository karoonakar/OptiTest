package com.sg.app.services;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sg.app.entities.DealScf;
import com.sg.app.rest.model.ProcessRequestDTO;
import com.sg.app.utils.HttpHandler;
import com.sg.app.utils.ScfHdfsFileReader;
import com.sg.app.utils.ScfHdfsFileWriter;
/**
 * @author Karoonakar
 *
 */
@Service
@Transactional
public class ProcessRequestService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ProcessRequestService.class);
	
	//static final String fileName = "C:/kkj/SCF_Deal_Data.csv";
	static final String fileName = "hdfs://10.0.2.15:8020/user/deals-input-files/SCF_Deal_Data.csv";
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	public void extractProcessRequest(ProcessRequestDTO processRequestDTO){
		
		try{
			List<DealScf> dealsList = ScfHdfsFileReader.readCsvFile(fileName);
			String extractedFileName = ScfHdfsFileWriter.writeCsvFile(fileName,dealsList, processRequestDTO);
			processRequestDTO.setExtractedFileName(extractedFileName);
			
			// update status by calling web service
			String url = buildUrl(processRequestDTO);
			String response = HttpHandler.httpCaller(url);
			LOGGER.info("Service response " + response);
			
			//update extracted filename in ML process queue
			Gson gson = new Gson();
			final String jsonString = gson.toJson(processRequestDTO);
	        MessageCreator messageCreator = new MessageCreator() {
	            @Override
	            public Message createMessage(Session session) throws JMSException {
	                return session.createTextMessage(jsonString);
	            }
	        };
	        jmsTemplate.send("OptiTestProcessQueue", messageCreator);
			
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.debug("Error in File extraction Process ");
		}
	}
	
	
	private String buildUrl(ProcessRequestDTO processRequestDTO){
		StringBuilder url = new StringBuilder("http://localhost:8090/api/updateProcessRequestStatus/?requestId=");
		url.append(processRequestDTO.getRequestId());
		url.append("&status=InProcess-FileExtracted");
		return url.toString();
	}
	
}
