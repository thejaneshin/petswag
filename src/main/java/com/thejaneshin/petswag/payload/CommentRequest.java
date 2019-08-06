package com.thejaneshin.petswag.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentRequest {
	@NotBlank
	@Size(max=255)
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
