package com.RMS.Model;

import java.util.ArrayList;
import java.util.List;

public class CandidateLanguageDetailModel {

	private Integer candidate_language_detail_id;
	private Integer candidate_id;
	private boolean wr = false;
	private boolean rd = false;
	private boolean speak = false;
	private Integer language_id;
	// private String language_proficiency_level;
	private List<CandidateLanguageDetailModel> candidateLanguageDetailsModelList = new ArrayList<CandidateLanguageDetailModel>();
	public Integer getCandidate_language_detail_id() {
		return candidate_language_detail_id;
	}
	public void setCandidate_language_detail_id(Integer candidate_language_detail_id) {
		this.candidate_language_detail_id = candidate_language_detail_id;
	}
	public Integer getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(Integer candidate_id) {
		this.candidate_id = candidate_id;
	}
	public boolean isWr() {
		return wr;
	}
	public void setWr(boolean wr) {
		this.wr = wr;
	}
	public boolean isRd() {
		return rd;
	}
	public void setRd(boolean rd) {
		this.rd = rd;
	}
	public boolean isSpeak() {
		return speak;
	}
	public void setSpeak(boolean speak) {
		this.speak = speak;
	}
	public Integer getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}
	public List<CandidateLanguageDetailModel> getCandidateLanguageDetailsModelList() {
		return candidateLanguageDetailsModelList;
	}
	public void setCandidateLanguageDetailsModelList(List<CandidateLanguageDetailModel> candidateLanguageDetailsModelList) {
		this.candidateLanguageDetailsModelList = candidateLanguageDetailsModelList;
	}

	
}
