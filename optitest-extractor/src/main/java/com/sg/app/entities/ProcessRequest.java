package com.sg.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * @author Karoonakar
 *
 */
@Entity
@Table(name = "Request_Status")
public class ProcessRequest implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "request_id")
	private Integer id;
	
	@Column(name = "request_title", nullable = false, length = 150)
	private String requestTitle;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;
	
	@Column(name="status")
	private String status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "processRequest", cascade = CascadeType.ALL)
	private Set<OptiTestProcess> optiTestProcess = new HashSet<OptiTestProcess>(0);
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRequestTitle() {
		return requestTitle;
	}
	public void setRequestTitle(String title) {
		this.requestTitle = title;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<OptiTestProcess> getOptiTestProcess() {
		return optiTestProcess;
	}
	public void setOptiTestProcess(Set<OptiTestProcess> optiTestProcess) {
		this.optiTestProcess = optiTestProcess;
	}
	@Override
	public String toString() {
		return "ProcessRequest [id=" + id + ", requestTitle=" + requestTitle
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", status=" + status + ", optiTestProcess=" + optiTestProcess
				+ "]";
	}
	
	
}
