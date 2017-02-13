package com.sg.app.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.sg.app.entities.HttpResponse;

/**
 * @author Karoonakar
 *
 */
public class HttpHandler {
		

	public static String httpCaller(String url){
        
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        HttpWorker httpWorker  = new HttpWorker(url);
        Future<HttpResponse> result = executor.submit(httpWorker);
        String resString = null;
        
        try{
        	resString = result.get().getResponseString(); 
        }catch (InterruptedException e ) {
              e.printStackTrace();
        }catch(ExecutionException e){
           	  e.printStackTrace();
        }catch(Exception e){
           	  e.printStackTrace();
        }
        
        executor.shutdown();
        return resString;
	}
}
