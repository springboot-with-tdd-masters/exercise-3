package com.example.exercisethree.response;

import com.example.exercisethree.model.Author;

public class AuthorResponse {
	
	private String createdAt;
	private String updatedAt;
	private Long id;
	private String name;
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
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
	
	public static AuthorResponse convertToAuthorResponse(Author authorEntity) {
		AuthorResponse response = new AuthorResponse();
		response.setCreatedAt(authorEntity.getCreatedDate().toString());
		response.setUpdatedAt(authorEntity.getLastModifiedDate().toString());
		response.setId(authorEntity.getId());
		response.setName(authorEntity.getName());
		
		return response;
	}
	
}
