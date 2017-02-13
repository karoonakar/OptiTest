package com.sg.app.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

/**
 * @author Karoonakar
 *
 */
@XmlRootElement
public class ProcessRequestDTO
{	
	private Integer requestId;
	private String codchn;
	private String batchDate;
	private String description;
	private List<PropertyDTO> propertyList;
	private String status;
	private String email;
	private String selectedPropertyList;
	private String extractedFileName;
	
	
	@Expose(serialize = false)
	private int pageNo;
	@Expose(serialize = false)
	private int pageSize = 10;
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getCodchn() {
		return codchn;
	}
	public void setCodchn(String codchn) {
		this.codchn = codchn;
	}
	public String getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	public List<PropertyDTO> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<PropertyDTO> propertyList) {
		this.propertyList = propertyList;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSelectedPropertyList() {
		return selectedPropertyList;
	}
	public void setSelectedPropertyList(String selectedPropertyList) {
		this.selectedPropertyList = selectedPropertyList;
	}
	public String getExtractedFileName() {
		return extractedFileName;
	}
	public void setExtractedFileName(String extractedFileName) {
		this.extractedFileName = extractedFileName;
	}	
}