package com.sg.app.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sg.app.rest.model.ProcessRequestDTO;
/**
 * @author Karoonakar
 *
 */
@Component
public class MessageReceivingService {

    @Autowired
    private ConfigurableApplicationContext context;
    
    @Autowired
    private ProcessRequestService processRequestService;
    
    @JmsListener(destination = "OptiTestRequestQueue")
    public void receiveMessage(String message) {
    	//context.close();
    	System.out.println("OptiTest Extractor Service Received <" + message + ">");
        Gson gson = new Gson();
        ProcessRequestDTO processRequest = gson.fromJson(message, ProcessRequestDTO.class);
        processRequestService.extractProcessRequest(processRequest);
    }
}
