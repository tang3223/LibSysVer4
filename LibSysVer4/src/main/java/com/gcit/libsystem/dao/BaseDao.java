package com.gcit.libsystem.dao;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

public abstract class BaseDao {
	
	@Autowired
	public JdbcTemplate template;
	
	protected Integer pageNum, pageSize = 10;
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
