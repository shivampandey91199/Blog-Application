package com.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageResponse {

	private List<PostDTO> content;
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private long totalElements;
	private boolean lastPage;
	private boolean firstPage;
	
}
