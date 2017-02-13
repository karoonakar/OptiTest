package com.sg.app.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sg.app.OptiTestSamplerApplication;
import com.sg.app.services.ProcessRequestService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OptiTestSamplerApplication.class)
@WebAppConfiguration
public class OptiTestSamplerApplicationTests {

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
