package com.stacksimply.restservices.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "authoritiescus")
public class Authority {

	@Id
	@SequenceGenerator(name = "incseqorder_gen", allocationSize = 1, sequenceName = "order_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incseqorder_gen")
	private Long id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
