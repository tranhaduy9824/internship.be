package com.hnv.api.service.common;

import java.util.Collection;

public class ResultPagination {
	private Collection 	lst;
	private Integer 	total;
	private Integer 	begin;
	private Integer 	nbPerPage;
	private String 		token;
	
	private Integer next;
	
	
	public  ResultPagination(Collection lst,  Integer total, Integer begin, Integer  nbPerPage) {
		this.lst 		= lst;
		this.total 		= total;
		this.begin 		= begin;
		this.nbPerPage 	= nbPerPage;
	}
	
	public  ResultPagination(Collection lst,  Integer total, Integer begin, Integer  nbPerPage,  String token) {
		this.lst 			= lst;
		this.total 			= total;
		this.begin 			= begin;
		this.nbPerPage 		= nbPerPage;
		this.token			= token;
	}
	public Collection reqList() {
		return this.lst;
	}
}
