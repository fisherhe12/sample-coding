package com.github.fisherhe12.common.domain;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Company {

	private Integer id;

	private String name;

	private Date establishTime;

	private List<Department> departmentList;
	
	private Department[] departmentArray;
	
	private BigInteger[] bigInt;
	
	private Map<Company, Department> departmentMap;

	public Company() {
		super();
	}
	
	public Company(Integer id, String name, Date establishTime, List<Department> departmentList) {
		super();
		this.id = id;
		this.name = name;
		this.establishTime = establishTime;
		this.departmentList = departmentList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public Date getEstablishTime() {
		return establishTime;
	}

	public void setEstablishTime(Date establishTime) {
		this.establishTime = establishTime;
	}

	public Department[] getDepartmentArray() {
		return departmentArray;
	}

	public void setDepartmentArray(Department[] departmentArray) {
		this.departmentArray = departmentArray;
	}

	public BigInteger[] getBigInt() {
		return bigInt;
	}

	public void setBigInt(BigInteger[] bigInt) {
		this.bigInt = bigInt;
	}

	public Map<Company, Department> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<Company, Department> departmentMap) {
		this.departmentMap = departmentMap;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
