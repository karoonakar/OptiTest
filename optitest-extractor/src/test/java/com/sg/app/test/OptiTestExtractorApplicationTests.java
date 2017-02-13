package com.sg.app.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sg.app.OptiTestExtractorApplication;
import com.sg.app.services.ProcessRequestService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OptiTestExtractorApplication.class)
@WebAppConfiguration
public class OptiTestExtractorApplicationTests {

	@Autowired
	private ProcessRequestService processRequestService;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void test_findRequest() {
		System.out.println("No Test");
	}
}
