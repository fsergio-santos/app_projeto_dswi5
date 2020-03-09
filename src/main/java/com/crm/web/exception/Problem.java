package com.crm.web.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Problem {

	private Integer status;        //código http.
	private LocalDateTime timestamp; // data hora em que ocorreu o problema
	private String type;     // uma uri que especifica o tipo do problema
	private String title;  // descrição do tipo do problema mais legivel possível
	private String detail;      //descrição sobre a ocorrencia do erro. 
	private String userMessage; 
	private List<Fields> fields;
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getUserMessage() {
		return userMessage;
	}
	
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
		
	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}
	
	public static Builder builder() {
        return new Builder();
    }
		
	
	public static class Builder {
		
		private Problem problem;

		public Builder() {
			this.problem = new Problem();
		}
		
		public Builder addStatus(Integer status) {
			 this.problem.status = status;
		     return this;			 
		}
		
		public Builder addTimestamp(LocalDateTime timestamp) {
			this.problem.timestamp = timestamp;
			return this;
		}
		
		public Builder addType(String type) {
		    this.problem.type = type;
			return this;
		}
		
		public Builder addTitle(String title) {
			this.problem.title = title;
			return this;
		}

		public Builder addDetail(String detail) {
			this.problem.detail = detail;
			return this;
		}
		
		public Builder addUserMessage(String userMessage) {
			this.problem.userMessage = userMessage;
			return this;
		}
		
		public Builder addListFields(List<Fields> fields) {
			this.problem.fields = fields;
			return this;
		}
		
	
		public Problem build() {
			return this.problem;
		}
	}
	
}
