package com.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	private Integer categoryId;
	
	@NotEmpty
	@Size(min = 3,max= 25)
	private String categoryTitle;
	@NotEmpty
	@Size(min = 10,max=100)
	private String categoryDescription;
}
