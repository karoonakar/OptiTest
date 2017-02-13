package com.sg.app.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sg.app.entities.CommandResponse;
import com.sg.app.entities.DealScf;
import com.sg.app.rest.model.ProcessRequestDTO;
import com.sg.app.utils.HttpHandler;
import com.sg.app.utils.ScfHdfsFileReader;
import com.sg.app.utils.ScfHdfsFileWriter;
import com.sg.app.utils.SystemCommandHandler;
/**
 * @author Karoonakar
 *
 */
@Service
@Transactional
public class ProcessRequestService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ProcessRequestService.class);
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	static final String sourceFileName = "hdfs://10.0.2.15:8020/user/deals-input-files/SCF_Deal_Data.csv";
	
	public void samplingProcessRequest(BlockingQueue<ProcessRequestDTO> processQueue){
		
		try{
			ProcessRequestDTO processRequestDTO = processQueue.take();
			
			StringBuilder command = new StringBuilder("spark-submit --packages com.databricks:spark-csv_2.10:1.4.0 /root/application/optitest-ml-engine/PySpark/ml_sampler.py ");
			command.append(processRequestDTO.getRequestId()+ " ");
			command.append(processRequestDTO.getExtractedFileName() + " ");
			command.append(processRequestDTO.getSelectedPropertyList().replace(" ", ""));
			
			LOGGER.info("Command String : " + command.toString());
			System.out.println("Running Clustering Process.... ");	
			CommandResponse commandResponse = SystemCommandHandler.SystemCaller(command.toString());
			String outputFileName = processCommandResponse(commandResponse,processRequestDTO);
			
			if(outputFileName!=null && !outputFileName.isEmpty()){
				// update status by calling web service
				String url = buildUrl(processRequestDTO,outputFileName);
				String httpResponse = HttpHandler.httpCaller(url);
				LOGGER.info("Service response " + httpResponse);
			}
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.debug("Error in Sampling Process ");
		}
	}
	
	private String processCommandResponse(CommandResponse response, ProcessRequestDTO processRequestDTO){
		
		String[] lines = response.getOutputString().split(System.getProperty("line.separator"));
		String outputFileName = "";
		if(lines.length >= 2){
			List<String> mediodIndexList = getMediodIndex(lines[0]);
			System.out.println("MediodIndexList String : " + mediodIndexList);	
			if(mediodIndexList.isEmpty()){
				LOGGER.debug("Unable to find any Mediod");
			}else{
				Map<Integer,DealScf> scfDealMap =  ScfHdfsFileReader.readCsvFile(sourceFileName);
				Map<Integer,DealScf> sampledScfDealMap = new HashMap<Integer,DealScf>();
				for(String indx : mediodIndexList){
					sampledScfDealMap.put(Integer.parseInt(indx.trim())+1, scfDealMap.get(Integer.parseInt(indx.trim())+1));
				}
				outputFileName = ScfHdfsFileWriter.writeCsvFile(sourceFileName, sampledScfDealMap, processRequestDTO);
			}
		}else{
			LOGGER.debug("ML Response Output : " + response.getOutputString());
		}
		
		return outputFileName;
	}
	
	private List<String> getMediodIndex(String mediodString){
		String indexString = mediodString.replace("best_choice:", "").replace("[", "").replace("]", "");
		List<String> mediodIndex = Arrays.asList(indexString.split(","));
		return mediodIndex;
	}	
	
	private String buildUrl(ProcessRequestDTO processRequestDTO, String outputFileName){
		StringBuilder url = new StringBuilder("http://localhost:8090/api/updateProcessRequestStatus/?requestId=");
		url.append(processRequestDTO.getRequestId());
		url.append("&status=Completed");
		url.append("&description=" + outputFileName);
		return url.toString();
	}
}