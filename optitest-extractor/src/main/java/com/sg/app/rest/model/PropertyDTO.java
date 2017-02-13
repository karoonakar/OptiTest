package com.sg.app.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Karoonakar
 *
 */
@XmlRootElement
public class PropertyDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String id; 
	private String label;
	
	public PropertyDTO(){
		
	}
	
	public PropertyDTO(String label) {
		this.id = label;
		this.label = label;
	}
	
	public PropertyDTO(String id, String label) {
		this.id = id;
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}