package com.wolf.springmvc.response;

import java.util.ArrayList;
import java.util.List;

public class PaginationResponse<T> extends MessageResponse {

	private boolean hasNext;
	
	private Long count;
	
	private String objectId;
	
	private List<T> list = new ArrayList<T>();

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}
