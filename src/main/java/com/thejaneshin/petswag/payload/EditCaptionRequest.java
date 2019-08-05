package com.thejaneshin.petswag.payload;

import javax.validation.constraints.Size;

public class EditCaptionRequest {
	@Size(max=255)
	private String caption;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}
