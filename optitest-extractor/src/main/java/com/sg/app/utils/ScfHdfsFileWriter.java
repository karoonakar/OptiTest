package com.sg.app.utils;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sg.app.entities.DealScf;
import com.sg.app.rest.model.ProcessRequestDTO;
/**
 * @author Karoonakar
 *
 */
public class ScfHdfsFileWriter {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ScfHdfsFileWriter.class);
	
	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	public static String writeCsvFile(String fileName, List<DealScf> ScfDeals, ProcessRequestDTO processRequestDTO) {
		
		Set <String> columnSet =  new HashSet<String>(Arrays.asList(processRequestDTO.getSelectedPropertyList().split(", "))); 
		
		BufferedWriter bw = null;
		CSVPrinter csvFilePrinter = null;
		String extractedFileName=null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
				
		try {
			String requestId = "Request_id-" + processRequestDTO.getRequestId() + ".csv";
			fileName = fileName.replace("deals-input-files", "optitest-extr-files");
			extractedFileName = fileName.replace(".csv", requestId);
			
			Configuration configuration = new Configuration();
			URI uri = new URI(extractedFileName);
			FileSystem hdfs = FileSystem.get(uri, configuration);
			Path path = new Path(uri);
			
			bw = new BufferedWriter(new OutputStreamWriter(hdfs.create(path)));
	        csvFilePrinter = new CSVPrinter(bw, csvFileFormat);
	        
	        //Create CSV file header
	        List<String> FILE_HEADER = new ArrayList<String>();
	        FILE_HEADER.add("numtra");
	        FILE_HEADER.addAll(Arrays.asList(processRequestDTO.getSelectedPropertyList().split(", ")));
	        csvFilePrinter.printRecord(FILE_HEADER);
			
			for (DealScf deal : ScfDeals) {
				List<String> dealRecord = new ArrayList<String>();
				dealRecord.add(deal.getNumtra());
				if(columnSet.contains("notionalCurrency")){
					dealRecord.add(deal.getNotionalCurrency());
				}
				if(columnSet.contains("maturity")){
					dealRecord.add(deal.getMaturity());
				}
				
				if(columnSet.contains("rateIndex")){
					dealRecord.add(deal.getRateIndex());	
				}
				
				if(columnSet.contains("flowFrequency")){
					dealRecord.add(deal.getFlowFrequency());
				}
				
				if(columnSet.contains("flowCurrency")){
					dealRecord.add(deal.getFlowCurrency());
				}
				
				if(columnSet.contains("fixedStrike")){
					dealRecord.add(deal.getFixedStrike());
				}
				
				if(columnSet.contains("notional")){
					dealRecord.add(deal.getNotional());
				}
				if(columnSet.contains("discountFlag")){
					dealRecord.add(deal.getDiscountFlag());
				}
				if(columnSet.contains("varaibleStrike")){
					dealRecord.add(deal.getVaraibleStrike());
				}
	            
				csvFilePrinter.printRecord(dealRecord);
			}
			
			LOGGER.debug("CSV file was created successfully on HDFS!!!");
			
		} catch (Exception e) {
			LOGGER.debug("Error in CsvFileWriter on HDFS!!!");
			e.printStackTrace();
		} finally {
			try {
				bw.flush();
				bw.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				LOGGER.debug("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
			}
		}
		
		return extractedFileName;
	}
}