package com.sg.app.rest.endpoints.admin;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sg.app.rest.model.AuthenticatedUser;
import com.sg.app.security.SecurityUser;

@RestController
@RequestMapping(value="/api/users/", produces=MediaType.APPLICATION_JSON_VALUE)
public class UserRestController 
{
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	@RequestMapping(value="/authenticatedUser", method=RequestMethod.GET)
	public ResponseEntity<AuthenticatedUser> getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!= null) {
			
			Object userDetails = authentication.getPrincipal();
			if(userDetails != null && userDetails instanceof SecurityUser){
				
				SecurityUser secUser = (SecurityUser) userDetails;
				String username = secUser.getUsername();
				List<String> roles = Arrays.asList(secUser.getDomainUser().getRole());
				AuthenticatedUser authenticatedUser = new AuthenticatedUser(username, roles);
				return new ResponseEntity<AuthenticatedUser>(authenticatedUser,HttpStatus.OK); 
			}
		}
		return new ResponseEntity<AuthenticatedUser>(HttpStatus.UNAUTHORIZED);
		
	}
	
}
