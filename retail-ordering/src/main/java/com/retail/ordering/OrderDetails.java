package com.retail.ordering;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.retail.ordering.entity.UserDetails;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class OrderDetails {

	@XmlElement
	private String referenceNumber;
	
	@XmlElement
	private UserDetails userDetails;
	
	@XmlElement
	private int quantity;
	
	@XmlElement
	private Timestamp orderedDateTime;
	
	@XmlElement
	private Timestamp dispatchedDateTime;
	
	@XmlElement
	private Timestamp deliveredDateTime;
	
	@XmlElement
	private String status;

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getOrderedDateTime() {
		return orderedDateTime;
	}

	public void setOrderedDateTime(Timestamp orderedDateTime) {
		this.orderedDateTime = orderedDateTime;
	}

	public Timestamp getDispatchedDateTime() {
		return dispatchedDateTime;
	}

	public void setDispatchedDateTime(Timestamp dispatchedDateTime) {
		this.dispatchedDateTime = dispatchedDateTime;
	}

	public Timestamp getDeliveredDateTime() {
		return deliveredDateTime;
	}

	public void setDeliveredDateTime(Timestamp deliveredDateTime) {
		this.deliveredDateTime = deliveredDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
