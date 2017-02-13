package com.sg.app.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.domain.Page;

import com.sg.app.entities.ProcessRequest;
import com.sg.app.utils.BeanCopyUtils;
/**
 * @author Karoonakar
 *
 */

@XmlRootElement
public class ProcessRequesResponseDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<ProcessRequest> processRequestList;
	private long totalRecords;
	private int currentPage;
	private int pageSize;
	private boolean hasNextPage;
	private boolean hasPrevPage;
	
	public ProcessRequesResponseDTO(){
		
	}
	
	public ProcessRequesResponseDTO(List<ProcessRequest> processRequestList, long totalRecords, int currentPage, int pageSize, boolean hasNextPage, boolean hasPrevPage) {
		this.processRequestList = processRequestList;
		this.totalRecords = totalRecords;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.hasNextPage = hasNextPage;
		this.hasPrevPage = hasPrevPage;
	}

	public ProcessRequesResponseDTO(Page<ProcessRequest> pageData) {
		List<ProcessRequest> processRequestList = pageData.getContent();
		this.processRequestList = new ArrayList<ProcessRequest>();
		
		for (ProcessRequest processRequest : processRequestList) {
			this.processRequestList.add(BeanCopyUtils.copy(processRequest));
		}
		
		this.totalRecords = pageData.getTotalElements();
		this.currentPage = pageData.getNumber();
		this.pageSize = pageData.getSize();
		this.hasNextPage = pageData.hasNext();
		this.hasPrevPage = pageData.hasPrevious();
	}


	public long getTotalRecords() {
		return totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public boolean isHasPrevPage() {
		return hasPrevPage;
	}

	public List<ProcessRequest> getProcessRequestList() {
		return processRequestList;
	}

	public void setProcessRequestList(List<ProcessRequest> processRequestList) {
		this.processRequestList = processRequestList;
	}

}
