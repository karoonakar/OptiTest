package com.sg.app.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.sg.app.entities.CommandResponse;


public class SystemCommandHandler {
			
	public static CommandResponse SystemCaller(String command){
        
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        SystemCommandWorker systemCommandWorker  = new SystemCommandWorker(command);
        Future<CommandResponse> result = executor.submit(systemCommandWorker);
        CommandResponse commandResponse = null;
        
        try{
        	commandResponse = result.get(); 
        }catch (InterruptedException e ) {
              e.printStackTrace();
        }catch(ExecutionException e){
           	  e.printStackTrace();
        }catch(Exception e){
           	  e.printStackTrace();
        }
        
        executor.shutdown();
        return commandResponse;
	}
}