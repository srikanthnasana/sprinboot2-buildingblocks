package com.stacksimply.restservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {
	@SequenceGenerator(name = "incseqorder_gen", allocationSize =1, sequenceName = "order_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incseqorder_gen")
	@Column(name = "OREDR_ID", updatable = false)
	@Id
	private Long oredrId;
	@Column(name="order_desc")
	private String orderdescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@CreationTimestamp
	private Date Creationtime;
	
	@UpdateTimestamp
	private Date lastupdate;

	public Order() {
		super();
	}

	public Long getOredrId() {
		return oredrId;
	}

	public void setOredrId(Long oredrId) {
		this.oredrId = oredrId;
	}

	public String getOrderdescription() {
		return orderdescription;
	}

	public void setOrderdescription(String orderdescription) {
		this.orderdescription = orderdescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationtime() {
		return Creationtime;
	}

	public void setCreationtime(Date creationtime) {
		Creationtime = creationtime;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	
	
	
	
	
}
