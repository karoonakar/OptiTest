package com.sg.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Karoonakar
 *
 */
@Entity
@Table(name = "Optitest_Process")
public class OptiTestProcess implements Serializable {

	private static final long serialVersionUID = 365512704046201789L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "request_id", insertable = false, updatable = false)
	private Integer requestId;

	@Column(name = "codchn", nullable = false, length = 50)
	private String codchn;

	@Column(name = "property")
	private String property;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", nullable = false)
	private ProcessRequest processRequest;

	public OptiTestProcess(Integer id, String codchn, String property) {
		this.id = id;
		this.property = property;
		this.codchn = codchn;
	}

	public OptiTestProcess(String codchn, String property) {
		this.property = property;
		this.codchn = codchn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodchn() {
		return codchn;
	}

	public void setCodchn(String codchn) {
		this.codchn = codchn;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public ProcessRequest getProcessRequest() {
		return processRequest;
	}

	public void setProcessRequest(ProcessRequest processRequest) {
		this.processRequest = processRequest;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "OptiTestProcess [id=" + id + ", requestId=" + requestId
				+ ", codchn=" + codchn + ", property=" + property + "]";
	}

	
}
