package com.RMS.Model;

import java.util.Date;
import java.util.List;

public class ReportModel {
	private Integer month;
	private Integer year;
	private Integer candidate_id;
	private String full_name;
	private String state;
	private String city;
	private String date_of_birth;
	private String gender;
	private String email_id;
	private String active_inactive;
	private Integer state_id;
	private Integer city_id;
	private String mobile_no;
	private Integer company_id;
	private String company_name;
	private String company_reg_no;
	private Integer telephone_no;
	private String job_name;
	private String employment_type;
	private String location;
	private Integer job_details_id;
	
	
	
	public Integer getJob_details_id() {
		return job_details_id;
	}
	public void setJob_details_id(Integer job_details_id) {
		this.job_details_id = job_details_id;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getEmployment_type() {
		return employment_type;
	}
	public void setEmployment_type(String employment_type) {
		this.employment_type = employment_type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_reg_no() {
		return company_reg_no;
	}
	public void setCompany_reg_no(String company_reg_no) {
		this.company_reg_no = company_reg_no;
	}
	
	
	public Integer getTelephone_no() {
		return telephone_no;
	}
	public void setTelephone_no(Integer telephone_no) {
		this.telephone_no = telephone_no;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	public String getActive_inactive() {
		return active_inactive;
	}
	public void setActive_inactive(String active_inactive) {
		this.active_inactive = active_inactive;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	
	public Integer getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(Integer candidate_id) {
		this.candidate_id = candidate_id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void add(ReportModel candidateDataList) {
		// TODO Auto-generated method stub
		
	}
	public Integer getState_id() {
		return state_id;
	}
	public void setState_id(Integer state_id) {
		this.state_id = state_id;
	}
	public Integer getCity_id() {
		return city_id;
	}
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	public void setTelephone_no(Object telephone_no2) {
		// TODO Auto-generated method stub
		
	}
	
}
