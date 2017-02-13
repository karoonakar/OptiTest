package com.sg.app.utils;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private static final String [] FILE_HEADER = {"numtra","notionalCurrency","maturity","rateIndex","flowFrequency","flowCurrency","fixedStrike","notional","discountFlag","varaibleStrike"};
	
	//Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	public static String writeCsvFile(String fileName, Map<Integer,DealScf> scfDealMap, ProcessRequestDTO processRequestDTO) {
		 
		BufferedWriter bw = null;
		CSVPrinter csvFilePrinter = null;
		String outputFileName=null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
				
		try {
			String requestId = "Request_id-" + processRequestDTO.getRequestId() + ".csv";
			fileName = fileName.replace("deals-input-files", "optitest-output-files");
			outputFileName = fileName.replace(".csv", requestId);
			
			Configuration configuration = new Configuration();
			URI uri = new URI(outputFileName);
			FileSystem hdfs = FileSystem.get(uri, configuration);
			Path path = new Path(uri);
			
			bw = new BufferedWriter(new OutputStreamWriter(hdfs.create(path)));
	        csvFilePrinter = new CSVPrinter(bw, csvFileFormat);
	        
	        //Create CSV file header
	        csvFilePrinter.printRecord(FILE_HEADER);
			
			for (Map.Entry<Integer,DealScf> entry : scfDealMap.entrySet()) {
				List<String> dealRecord = new ArrayList<String>();
					dealRecord.add(entry.getValue().getNumtra());
					dealRecord.add(entry.getValue().getNotionalCurrency());
					dealRecord.add(entry.getValue().getMaturity());
					dealRecord.add(entry.getValue().getRateIndex());	
					dealRecord.add(entry.getValue().getFlowFrequency());
					dealRecord.add(entry.getValue().getFlowCurrency());
					dealRecord.add(entry.getValue().getFixedStrike());
					dealRecord.add(entry.getValue().getNotional());
					dealRecord.add(entry.getValue().getDiscountFlag());
					dealRecord.add(entry.getValue().getVaraibleStrike());
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
		
		return outputFileName;
	}
}