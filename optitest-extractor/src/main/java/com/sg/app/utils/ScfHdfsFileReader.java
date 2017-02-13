package com.sg.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sg.app.entities.DealScf;
/**
 * @author Karoonakar
 *
 */

public class ScfHdfsFileReader {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ScfHdfsFileReader.class);
	
	//CSV file header
    private static final String [] FILE_HEADER = {"numtra","notionalCurrency","maturity","rateIndex","flowFrequency","flowCurrency","fixedStrike","notional","discountFlag","varaibleStrike"};
	
	private static final String NUMTRA ="numtra";
	private static final String NOTIONAL_CURRENCY ="notionalCurrency";
	private static final String MATURITY="maturity";
	private static final String INDEX="rateIndex";
	private static final String FLOW_FREQUENCY= "flowFrequency";
	private static final String FLOW_CURRENCY = "flowCurrency";
	private static final String FIXED_STRIKE = "fixedStrike";
	private static final String NOTIONAL = "notional";
	private static final String DISCOUNT_FLAG = "discountFlag";
	private static final String VARIABLE_STRIKE = "varaibleStrike";
	
	public static List<DealScf> readCsvFile(String fileName) {

		BufferedReader br = null;
		CSVParser csvFileParser = null;
		
		//Create the CSVFormat object with the header mapping
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);
        
        //Create a new list of deals to be filled by CSV file data 
    	List<DealScf> scfDeals = new ArrayList<DealScf>();
     
        try {
        	
        	Configuration configuration = new Configuration();
			URI uri = new URI(fileName);
			FileSystem hdfs = FileSystem.get(uri, configuration);
			Path path = new Path(uri);
			br = new BufferedReader(new InputStreamReader(hdfs.open(path)));
			
            //initialize CSVParser object
            csvFileParser = new CSVParser(br, csvFileFormat);
            
            //Get a list of CSV file records
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            
            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
            	CSVRecord record = csvRecords.get(i);
            	
            	DealScf dealData = new DealScf(record.get(NUMTRA), record.get(NOTIONAL_CURRENCY), record.get(MATURITY), record.get(INDEX), record.get(FLOW_FREQUENCY),
            			record.get(FLOW_CURRENCY),record.get(FIXED_STRIKE),record.get(NOTIONAL),record.get(DISCOUNT_FLAG),record.get(VARIABLE_STRIKE));
            	scfDeals.add(dealData);	
			}
            
        } 
        catch (Exception e) {
        	LOGGER.debug("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                br.close();
                csvFileParser.close();
            } catch (IOException e) {
            	LOGGER.debug("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }
        
        return scfDeals;
	}

}