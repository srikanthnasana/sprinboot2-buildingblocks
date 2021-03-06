package com.stacksimply.restservices.projections;
//DTO projections
public class SimpleUserProjectionDTO {

	private String firstName;
	private String lastName;
	private Long id;
	

	public SimpleUserProjectionDTO(String firstName, String lastName, Long id) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
