package com.train.app.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sg.app.OptiTestApplication;
import com.sg.app.entities.ProcessRequest;
import com.sg.app.rest.model.ProcessRequestDTO;
import com.sg.app.services.ProcessRequestService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OptiTestApplication.class)
@WebAppConfiguration
public class OptiTestApplicationTests {

	@Autowired
	private ProcessRequestService processRequestService;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void test_findPosts() {
		ProcessRequestDTO processRequest = new ProcessRequestDTO();
		Page<ProcessRequest> posts = processRequestService.findProcessRequest(processRequest );
		System.out.println(posts);
	}
}
