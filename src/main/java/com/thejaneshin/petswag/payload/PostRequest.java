package com.thejaneshin.petswag.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostRequest {
	@NotBlank
	@Size(max=255)
	private String image;
	
	@Size(max=255)
	private String caption;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	
}
