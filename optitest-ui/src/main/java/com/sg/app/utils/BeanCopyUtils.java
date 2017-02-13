package com.sg.app.utils;

import com.sg.app.entities.ProcessRequest;
import com.sg.app.entities.User;
/**
 * @author Karoonakar
 *
 */

public final class BeanCopyUtils
{
	private BeanCopyUtils()
	{
	}
	
	public static User copy(User user)
	{
		if(user == null)
		{
			return null;
		}
		User clone = new User();
		clone.setId(user.getId());
		clone.setEmail(user.getEmail());
		clone.setPassword(user.getPassword());
		clone.setName(user.getName());
		return clone;
	}
	
	public static ProcessRequest copy(ProcessRequest processRequest)
	{
		if(processRequest == null){
			return null;
		}
		ProcessRequest clone = new ProcessRequest();
		clone.setId(processRequest.getId());
		clone.setRequestTitle(processRequest.getRequestTitle());
		clone.setCreatedOn(processRequest.getCreatedOn());
		clone.setUpdatedOn(processRequest.getUpdatedOn());
		clone.setStatus(processRequest.getStatus());
		
		return clone;
	}
	
}
