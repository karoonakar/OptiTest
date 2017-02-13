package com.sg.app.services;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
    
    private BlockingQueue<ProcessRequestDTO> processQueue = new ArrayBlockingQueue<ProcessRequestDTO>(10);
    
    @JmsListener(destination = "OptiTestProcessQueue")
    public void receiveMessage(String message) {
    	
    	System.out.println("OptiTest Sampler Service Received <" + message + ">");
        Gson gson = new Gson();
        ProcessRequestDTO processRequest = gson.fromJson(message, ProcessRequestDTO.class);
        processQueue.add(processRequest);
        processRequestService.samplingProcessRequest(processQueue);

    }
}
